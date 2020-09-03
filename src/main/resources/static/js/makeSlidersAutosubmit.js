// This is for entries.html
(function() {
   var makeAutosubmit = function(element) {
       element.onchange = function() { this.form.submit() };
   }
   document.getElementsByName("seeAllCats").forEach(makeAutosubmit);
   document.getElementsByName("publicity").forEach(makeAutosubmit);
})();