function removeDuplicatesAndSort(arr) {
    // set 이 중복을 제거 해준다
    let result = [...new Set(arr)];

    // 문자열 리스트에 sort만 사용하면 자동 정렬
    return result.sort();
}

console.log(removeDuplicatesAndSort(["apple", "banana", "apple", "cherry"]))