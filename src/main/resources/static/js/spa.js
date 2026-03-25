document.addEventListener("DOMContentLoaded", () => {
  // Load page dynamically
  function loadPage(url) {
    fetch(url)
      .then((res) => res.text())
      .then((html) => {
        // Extract the content fragment
        const parser = new DOMParser();
        const doc = parser.parseFromString(html, "text/html");
        const newContent = doc.querySelector("#spa-content") || doc.body;
        document.getElementById("content").innerHTML = newContent.innerHTML;

        // Update URL
        history.pushState(null, "", url);

        // Update active navbar
        document.querySelectorAll(".nav-link").forEach((link) => {
          link.classList.remove("active");
          if (link.getAttribute("href") === url) link.classList.add("active");
        });
      });
  }

  // Attach click handlers
  document.querySelectorAll(".nav-link").forEach((link) => {
    link.addEventListener("click", function (e) {
      e.preventDefault();
      const url = this.getAttribute("href");
      loadPage(url);
    });
  });

  // Handle browser back/forward buttons
  window.onpopstate = function () {
    loadPage(location.pathname);
  };
});
