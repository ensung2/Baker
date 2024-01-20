// 레시피 삭제
const deleteButton = document.getElementById('Delete_btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('section-id').value;
        fetch(`/recipe/new/${id}`, {
            method: 'DELETE'
        })
            .then(()=> {
                alert('레시피 삭제가 완료되었습니다.');
                location.replace('/list');
            });
    });
}

// 레시피 수정
const modifyButton = document.getElementById('Modify_btn');


if (modifyButton) {
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        fetch(`/recipe/new/${id}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                itemType: document.getElementById('itemType').value,
                itemName: document.getElementById('itemName').value,
                info: document.getElementById('info').value,
                material: document.getElementById('material').value,
                recipe: document.getElementById('recipe').value
            })
        })
            .then(()=>{
                alert('레시피 수정이 완료되었습니다.');
                location.replace(`/list/${id}`);
            });
    });
}

// 레시피 등록(저장)
const createButton = document.getElementById('Create_btn');

if (createButton) {
    createButton.addEventListener('click', (event) => {
        fetch("/recipe/new", {
            method: "POST",
            headers: {
                "Content-Type" : "application/json",
        },
            body: JSON.stringify({
                itemType: document.getElementById("itemType").value,
                itemName: document.getElementById("itemName").value,
                info: document.getElementById("info").value,
                material: document.getElementById("material").value,
                recipe: document.getElementById("recipe").value
            }),
        }).then(()=>{
            alert("레시피 등록이 완료되었습니다.");
            location.replace("/list");
        });
    });
}
