// login.js — handles password toggle and forgot-password panel

(function () {
  "use strict";

  // ── Password visibility toggle ────────────────────────────────────────────
  const passwordInput = document.getElementById("password");
  const togglePasswordBtn = document.getElementById("togglePasswordBtn");
  const eyeIcon = document.getElementById("eyeIcon");

  const EYE_OPEN = `
    <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/>
    <circle cx="12" cy="12" r="3"/>
  `;

  const EYE_CLOSED = `
    <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8
             a18.45 18.45 0 0 1 5.06-5.94"/>
    <path d="M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8
             a18.5 18.5 0 0 1-2.16 3.19"/>
    <line x1="1" y1="1" x2="23" y2="23"/>
  `;

  if (togglePasswordBtn && passwordInput && eyeIcon) {
    togglePasswordBtn.addEventListener("click", function () {
      const isHidden = passwordInput.type === "password";
      passwordInput.type = isHidden ? "text" : "password";
      eyeIcon.innerHTML = isHidden ? EYE_CLOSED : EYE_OPEN;
    });
  }

  // ── Forgot password panel expand / collapse ───────────────────────────────
  const forgotPasswordPanel = document.getElementById("forgotPasswordPanel");
  const forgotPasswordBtn = document.getElementById("forgotPasswordBtn");
  const closeForgotPasswordBtn = document.getElementById(
    "closeForgotPasswordBtn",
  );
  const resetEmailInput = document.getElementById("resetEmail");

  let forgotOpen = false;

  function openForgotPanel() {
    forgotOpen = true;
    forgotPasswordPanel.style.gridTemplateRows = "1fr";
    forgotPasswordPanel.style.opacity = "1";
    setTimeout(function () {
      if (resetEmailInput) resetEmailInput.focus();
    }, 360);
  }

  function closeForgotPanel() {
    forgotOpen = false;
    forgotPasswordPanel.style.gridTemplateRows = "0fr";
    forgotPasswordPanel.style.opacity = "0";
  }

  if (forgotPasswordBtn && forgotPasswordPanel) {
    forgotPasswordBtn.addEventListener("click", function () {
      forgotOpen ? closeForgotPanel() : openForgotPanel();
    });
  }

  if (closeForgotPasswordBtn && forgotPasswordPanel) {
    closeForgotPasswordBtn.addEventListener("click", closeForgotPanel);
  }
})();
