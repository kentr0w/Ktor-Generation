window.addEventListener("load", function () {
  var treeitems = document.querySelectorAll('[role="treeitem"]');

  for (var i = 0; i < treeitems.length; i++) {
    treeitems[i].addEventListener("click", function (event) {
      var treeitem = event.currentTarget;
      var label = treeitem.getAttribute("aria-label");
      if (!label) {
        label = treeitem.innerHTML;
      }

      document.getElementById("last_action").value = label.trim();

      event.stopPropagation();
      event.preventDefault();
    });
  }
});
