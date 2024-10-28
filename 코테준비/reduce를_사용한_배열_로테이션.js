function groupItems(items) {
    if (items.length < 1) return {
        fruits: [],
        vegetables: [],
        totalItems: 0
    }

    return items.reduce((grouped, item, index, array) => {
        if (item.type === 'fruit') {
            grouped.fruits.push(item.name);
        } else if (item.type === 'vegetable') {
            grouped.vegetables.push(item.name);
        }

        if (index === array.length - 1) {
            grouped.totalItems = array.length;
        }

        return grouped;
    }, {fruits: [], vegetables: [], totalItems: 0})
}

const items = [
    { name: 'Apple', type: 'fruit' },
    { name: 'Carrot', type: 'vegetable' },
    { name: 'Banana', type: 'fruit' },
    { name: 'Broccoli', type: 'vegetable' }
  ];
  
  const groupedItems = groupItems(items);
  console.log(groupedItems);
  // 출력:
  // {
  //   fruits: ['Apple', 'Banana'],
  //   vegetables: ['Carrot', 'Broccoli'],
  //   totalItems: 4
  // }