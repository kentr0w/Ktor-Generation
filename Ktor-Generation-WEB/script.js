var toggler = document.getElementsByClassName("caret");
var i;
var isLeftMenuOpen = true;
var leftMenuDisplayStyle;
var secondAndThirdSectionsWidht;
var arr = [1, 2, 3];

console.log(toggler.length);
for (i = 0; i < toggler.length; i++) {
  toggler[i].addEventListener("click", function () {
    console.log(1);
    this.parentElement.querySelector(".nested").classList.toggle("active");
    this.classList.toggle("caret-down");
  });
}

function hideLeftMenu() {
  var width;
  if (isLeftMenuOpen) {
    width = "35px";
    leftMenuDisplayStyle = "none";
    secondAndThirdSectionsWidht = "1865px";
  } else {
    width = "350px";
    leftMenuDisplayStyle = "block";
    secondAndThirdSectionsWidht = "1550px";
  }

  document.getElementById("rightMenuId").style.width = width;
  document.getElementById("mainBoardId").style.marginLeft = width;
  document.getElementById(
    "rightMenuContentId"
  ).style.display = leftMenuDisplayStyle;
  document.getElementById("configTextId").style.display = leftMenuDisplayStyle;
  document.getElementById(
    "generateProjectButtonTextId"
  ).style.display = leftMenuDisplayStyle;
  isLeftMenuOpen = !isLeftMenuOpen;
  document.getElementById(
    "mainBoardId"
  ).style.width = secondAndThirdSectionsWidht;
}

function createMenuItem(name) {
  let li = document.createElement("li");
  li.textContent = name;
  return li;
}

let activities = document.getElementById("buildTypeProjectId");

activities.addEventListener("change", function () {
  console.log(10);
});

function generate() {
  // const menu = document.querySelector("#menu");
  // menu.appendChild(createMenuItem("Home"));
  // menu.appendChild(createMenuItem("Services"));
  // menu.appendChild(createMenuItem("About Us"));
  arr.push(Math.floor(Math.random() * Math.floor(100)));
  arr.forEach((element) => console.log(element));
}
