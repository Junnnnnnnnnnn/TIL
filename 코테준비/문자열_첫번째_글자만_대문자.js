function capitalizeWords(input) {
    // 예외 처리
    if(!input) return '';

    // filter 를 사용해 빈 문자열 예외처리
    // charAt 을 사용해 문자열의 문자 확인
    // slice 를 사용해 문자열의 필요한 곳만 사용
    return input.split(' ').filter((v) => v.length > 0).map((v) => v.charAt(0).toUpperCase() + v.slice(1)).join(' ');
}

const input = "javaScript is awesome";
const result = capitalizeWords(input);
console.log(result);
// 출력: "JavaScript Is Awesome"