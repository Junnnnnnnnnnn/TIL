import requests
from bs4 import BeautifulSoup
url = "https://finance.naver.com/sise/"
response = requests.get(url)
soup = BeautifulSoup(response.text , "html.parser")
#kospi =  soup.find("span", {"id" : "KOSPI_now"}).string
kospi = soup.select_one("#KOSPI_now")
print(kospi)
