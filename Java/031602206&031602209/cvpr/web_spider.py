import re
from multiprocessing import Pool, Manager
import requests
from requests.exceptions import ConnectionError
from functools import partial
from lxml import etree
from bs4 import BeautifulSoup


def get_page(url):
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36'
    }
    response = requests.get(url, headers=headers)
    if response.status_code == 200:
        return response.text
    return None


def get_urls():
    text = get_page('http://openaccess.thecvf.com/CVPR2018.py')
    html = etree.HTML(text)
    links = html.xpath('//dt/a//@href')
    urls = []
    for link in links:
        url = 'http://openaccess.thecvf.com/' + link
        urls.append(url)
    return urls


def scrape(lock, data, url):
    try:
        text = get_page(url)

        pdf_links, abstracts, authors, titles, booktitles, months, years = data
        # print(text)
        html = etree.HTML(text)
        pdf_link = html.xpath('//a[contains(text(), "pdf")]/@href')[0]
        pdf_link = 'http://openaccess.thecvf.com/' + pdf_link[6:]

        abstract = html.xpath('//div[@id="abstract"]/text()')[0]
        abstract = abstract[1:]

        soup = BeautifulSoup(text, 'lxml')
        detail = soup.find(class_="bibref")

        regex = '(\w+) = {([\S\xa0 ]+)}'
        results = re.findall(regex, detail.getText())
        author = results[0][1]
        title = results[1][1]
        booktitle = results[2][1]
        month = results[3][1]
        year = results[4][1]
        lock.acquire()

        pdf_links.append(pdf_link)
        abstracts.append(abstract)
        authors.append(author)
        titles.append(title)
        booktitles.append(booktitle)
        months.append(month)
        years.append(year)

        lock.release()
    except ConnectionError:
        print('Error Occured ', url)
    finally:
        print('URL ', url, ' Scraped')


if __name__ == '__main__':
    pool = Pool(processes=8)
    manager = Manager()
    lock = manager.Lock()

    pdf_links = manager.list()
    abstracts = manager.list()
    authors = manager.list()
    titles = manager.list()
    booktitles = manager.list()
    months = manager.list()
    years = manager.list()

    data = (pdf_links, abstracts, authors, titles, booktitles, months, years)

    partial_scrape = partial(scrape, lock, data)
    urls = get_urls()
    pool.map(partial_scrape, urls)

    print(len(pdf_links))
    print(len(abstracts))
    print(len(authors))
    print(len(titles))
    print(len(booktitles))
    print(len(months))
    print(len(years))

    with open('result.txt', 'w', encoding='utf-8') as file:
        for i in range(len(titles)):
            file.write(str(i)+'\n')
            file.write('Title: ' + titles[i] + '\n')
            file.write('Abstract: ' + abstracts[i] + '\n')
            file.write('\n\n')
