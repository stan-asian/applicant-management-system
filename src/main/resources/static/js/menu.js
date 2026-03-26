(function () {
  const toggle = document.getElementById("menu-toggle");
  const menu = document.getElementById("mobile-menu");
  const hb1 = document.getElementById("hb1");
  const hb2 = document.getElementById("hb2");
  const hb3 = document.getElementById("hb3");
  let isOpen = false;

  function openMenu() {
    isOpen = true;
    toggle.setAttribute("aria-expanded", "true");
    toggle.setAttribute("aria-label", "Close navigation menu");
    menu.classList.remove("hidden");
    hb1.style.transform = "translateY(7px) rotate(45deg)";
    hb2.style.opacity = "0";
    hb3.style.transform = "translateY(-7px) rotate(-45deg)";
  }

  function closeMenu() {
    isOpen = false;
    toggle.setAttribute("aria-expanded", "false");
    toggle.setAttribute("aria-label", "Open navigation menu");
    menu.classList.add("hidden");
    hb1.style.transform = "";
    hb2.style.opacity = "";
    hb3.style.transform = "";
  }

  toggle.addEventListener("click", function () {
    isOpen ? closeMenu() : openMenu();
  });

  // Close on outside click
  document.addEventListener("click", function (e) {
    if (isOpen && !toggle.contains(e.target) && !menu.contains(e.target)) {
      closeMenu();
    }
  });

  // Close on Escape key
  document.addEventListener("keydown", function (e) {
    if (isOpen && e.key === "Escape") closeMenu();
  });
})();
