document.addEventListener("DOMContentLoaded", () => {
  function showToast(id) {
    const toast = document.getElementById(id);
    if (!toast) return;

    // animate in
    setTimeout(() => {
      toast.classList.remove("opacity-0", "translate-y-[-10px]");
      toast.classList.add("opacity-100", "translate-y-0");
    }, 100);

    // auto hide after 3 seconds
    setTimeout(() => {
      toast.classList.add("opacity-0", "translate-y-[-10px]");
      toast.classList.remove("opacity-100", "translate-y-0");

      // remove from DOM after animation
      setTimeout(() => toast.remove(), 300);
    }, 3000);
  }

  showToast("toast-success");
  showToast("toast-error");
});
