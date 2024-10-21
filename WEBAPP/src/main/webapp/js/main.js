const APIURL = "http://www.omdbapi.com/?apikey=da0e1348&";
const selects = document.querySelectorAll(".select");
const form = document.querySelector("#form");
const searchInput = document.querySelector("#search");
const movies = document.querySelectorAll(".movie");
let typingTimer;

function saveFocus() {
    const activeElementId = document.activeElement.id;
    sessionStorage.setItem('focusedElementId', activeElementId);
}

document.getElementById('form').addEventListener('submit', saveFocus);
let reloadData = () => {
    form.submit();
};

let loadImages = async () => {
    for (const movie of movies) {
        const title = movie.querySelector("img").alt;
        const url = `${APIURL}t=${title}`;
        console.log(url);
        const resp = await fetch(url);
        const respData = await resp.json();

        console.log(respData);
        movie.querySelector("img").src = respData.Poster;
        movie.querySelector(".overview").querySelector(".overview-info").textContent = respData.Plot;

    }
}

selects.forEach(item => {
    item.addEventListener("change", () => {
        reloadData();
    });
});

searchInput.addEventListener("input", () => {
    clearTimeout(typingTimer);
    typingTimer = setTimeout(reloadData, 500);
});

document.addEventListener('keyup', event => {
    if (event.code === 'Enter') {
        reloadData();
        console.log('enter was pressed');
    }
});

window.onload = function () {
    const focusedElementId = sessionStorage.getItem('focusedElementId');
    if (focusedElementId) {
        const element = document.getElementById(focusedElementId);
        if (element) {
            element.focus();
            if (element.tagName.toLowerCase() === 'input' && element.type === 'text') {
                const val = element.value;
                element.value = '';
                element.value = val;
            }
        }
    }
};

loadImages();





