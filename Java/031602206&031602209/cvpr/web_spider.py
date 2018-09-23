import re
import requests
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
    urls = html.xpath('//dt/a//@href')
    return urls


def main():
    urls = get_urls()
    for url in urls:
        link = 'http://openaccess.thecvf.com/' + url
        text = get_page(link)
        if text is None:
            continue
        html = etree.HTML(text)
        pdf_link = html.xpath('//a[contains(text(), "pdf")]/@href')[0]
        pdf_link = 'http://openaccess.thecvf.com/' + pdf_link[6:]
        # print(pdf_link)

        abstract = html.xpath('//div[@id="abstract"]/text()')[0]
        abstract = abstract[1:]
        # print(abstract)

        soup = BeautifulSoup(text, 'lxml')
        detail = soup.find(class_="bibref")
        # print(detail.getText())
        regex = '(\w+) = {([\S\xa0 ]+)}'
        results = re.findall(regex, detail.getText())
        author = results[0][1]
        # print(author)
        title = results[1][1]
        print(title)
        print(results)
        booktitle = results[2][1]
        # print(booktitle)
        month = results[3][1]
        # print(month)
        year = results[4][1]
        # print(year)


if __name__ == '__main__':
    main()
