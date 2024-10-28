function printHelloWorld() {
    const str = 'Hello World!';
    
    str.split(' ').filter((v) => v.length > 0).forEach((item => {
        setTimeout(() => {
            console.log(item);
        }, 1000)
    }))
} 

printHelloWorld();