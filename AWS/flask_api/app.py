from flask import Flask

app = Flask(__name__)

@app.route("/json",methods = ['GET'])
def getString():
    return "안녕하세요"

if __name__ == "__main__":
    app.run(host='0.0.0.0')