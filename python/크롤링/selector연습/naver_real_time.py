import requests
from bs4 import BeautifulSoup
url = "https://www.naver.com/"
response = requests.get(url)
soup = BeautifulSoup(response.text , "html.parser")
#kospi =  soup.find("span", {"id" : "KOSPI_now"}).string
for index in range(1,21):
    list_rt = soup.select_one(f"#PM_ID_ct > div.header > div.section_navbar > div.area_hotkeyword.PM_CL_realtimeKeyword_base > div.ah_roll.PM_CL_realtimeKeyword_rolling_base > div > ul > li:nth-child({index}) > a > span.ah_k")
    print(list_rt.string)

