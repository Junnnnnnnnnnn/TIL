function debounce(func, ms) {
    let timerId;

    return function(...args) {
        const context = this;

        if (timerId) {
            clearTimeout(timerId);
        }

        timerId = setTimeout(() => {
            func.apply(context, args);
        }, ms)
    }
}

const user = {
    name: 'miju',
    hello(message) {
        console.log(`${this.name} ${message}~`);
    }
}

user.hello = debounce(user.hello, 1000);

user.hello('1');
user.hello('2');
user.hello('3');