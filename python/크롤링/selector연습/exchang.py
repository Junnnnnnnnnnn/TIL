import requests
from bs4 import BeautifulSoup
url = "https://finance.naver.com/marketindex"
response = requests.get(url)
soup = BeautifulSoup(response.text , "html.parser")
#kospi =  soup.find("span", {"id" : "KOSPI_now"}).string
daller = soup.select_one("#exchangeList > li.on > a.head.usd > div > span.value")
    
print(daller.string)

