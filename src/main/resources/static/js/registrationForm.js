// register.js — handles password toggles, strength meter, match validation, promo code formatting

(function () {
  "use strict";

  // ── SVG paths ─────────────────────────────────────────────────────────────
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

  // ── Generic password visibility toggle factory ────────────────────────────
  function makeToggle(btnId, inputId, iconId) {
    var btn = document.getElementById(btnId);
    var input = document.getElementById(inputId);
    var icon = document.getElementById(iconId);
    if (!btn || !input || !icon) return;
    btn.addEventListener("click", function () {
      var hidden = input.type === "password";
      input.type = hidden ? "text" : "password";
      icon.innerHTML = hidden ? EYE_CLOSED : EYE_OPEN;
    });
  }

  makeToggle("toggleRegisterPasswordBtn", "registerPassword", "regEyeIcon");
  makeToggle("toggleConfirmPasswordBtn", "confirmPassword", "confirmEyeIcon");

  // ── Password strength meter ───────────────────────────────────────────────
  var passwordInput = document.getElementById("registerPassword");
  var bars = [
    document.getElementById("bar1"),
    document.getElementById("bar2"),
    document.getElementById("bar3"),
    document.getElementById("bar4"),
  ];
  var strengthLabel = document.getElementById("strengthLabel");

  var STRENGTH_CONFIG = [
    { label: "", color: "rgba(37,79,34,0.12)" }, // 0 — empty
    { label: "Weak", color: "#e05252" }, // 1
    { label: "Fair", color: "#e8a838" }, // 2
    { label: "Good", color: "#6aab4f" }, // 3
    { label: "Strong", color: "#254f22" }, // 4
  ];

  function scorePassword(pwd) {
    if (!pwd) return 0;
    var score = 0;
    if (pwd.length >= 8) score++;
    if (pwd.length >= 12) score++;
    if (/[A-Z]/.test(pwd) && /[a-z]/.test(pwd)) score++;
    if (/[0-9]/.test(pwd)) score++;
    if (/[^A-Za-z0-9]/.test(pwd)) score++;
    // clamp to 1–4 once typing begins
    return Math.min(4, Math.max(1, score));
  }

  function updateStrength() {
    var pwd = passwordInput ? passwordInput.value : "";
    var score = pwd.length === 0 ? 0 : scorePassword(pwd);
    var cfg = STRENGTH_CONFIG[score];

    bars.forEach(function (bar, i) {
      if (!bar) return;
      bar.style.background = i < score ? cfg.color : "rgba(37,79,34,0.12)";
    });

    if (strengthLabel) {
      strengthLabel.textContent = score > 0 ? cfg.label : "";
      strengthLabel.style.color = score > 0 ? cfg.color : "rgba(37,79,34,0.4)";
    }
  }

  if (passwordInput) {
    passwordInput.addEventListener("input", function () {
      updateStrength();
      checkMatch(); // re-run match whenever password changes
    });
  }

  // ── Confirm password match hint ───────────────────────────────────────────
  var confirmInput = document.getElementById("confirmPassword");
  var matchHint = document.getElementById("matchHint");

  function checkMatch() {
    if (!confirmInput || !matchHint) return;
    var pwd = passwordInput ? passwordInput.value : "";
    var confirm = confirmInput.value;

    if (!confirm) {
      matchHint.textContent = "";
      confirmInput.style.borderColor = "rgba(37,79,34,0.2)";
      return;
    }

    if (pwd === confirm) {
      matchHint.textContent = "✓ Passwords match";
      matchHint.style.color = "#6aab4f";
      confirmInput.style.borderColor = "#6aab4f";
    } else {
      matchHint.textContent = "✗ Passwords do not match";
      matchHint.style.color = "#e05252";
      confirmInput.style.borderColor = "#e05252";
    }
  }

  if (confirmInput) {
    confirmInput.addEventListener("input", checkMatch);
  }

  // ── Promo code — auto-uppercase ───────────────────────────────────────────
  var promoInput = document.getElementById("promoCode");
  var promoHint = document.getElementById("promoHint");
  var promoIndicator = document.getElementById("promoIndicator");

  var CHECK_ICON = `
    <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4" viewBox="0 0 24 24"
      fill="none" stroke="#6aab4f" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
      <polyline points="20 6 9 17 4 12"/>
    </svg>
  `;
  var X_ICON = `
    <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4" viewBox="0 0 24 24"
      fill="none" stroke="#e05252" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
      <line x1="18" y1="6" x2="6" y2="18"/>
      <line x1="6" y1="6" x2="18" y2="18"/>
    </svg>
  `;

  if (promoInput) {
    promoInput.addEventListener("input", function () {
      // Keep cursor position while uppercasing
      var start = promoInput.selectionStart;
      var end = promoInput.selectionEnd;
      promoInput.value = promoInput.value.toUpperCase();
      promoInput.setSelectionRange(start, end);

      var val = promoInput.value.trim();

      if (!val) {
        promoHint.textContent = "";
        promoIndicator.innerHTML = "";
        promoIndicator.style.opacity = "0";
        promoInput.style.borderColor = "rgba(37,79,34,0.2)";
        return;
      }

      // Basic client-side format check (letters + digits, 4–20 chars)
      // Server will do the real validation
      var validFormat = /^[A-Z0-9]{4,20}$/.test(val);

      if (validFormat) {
        promoHint.textContent = "Code will be verified on submit.";
        promoHint.style.color = "rgba(37,79,34,0.45)";
        promoIndicator.innerHTML = CHECK_ICON;
        promoIndicator.style.opacity = "1";
        promoInput.style.borderColor = "rgba(37,79,34,0.35)";
      } else {
        promoHint.textContent = "Invalid format — letters and numbers only.";
        promoHint.style.color = "#e05252";
        promoIndicator.innerHTML = X_ICON;
        promoIndicator.style.opacity = "1";
        promoInput.style.borderColor = "#e05252";
      }
    });
  }

  // ── Form submit guard — block if passwords don't match ────────────────────
  var registerForm = document.getElementById("registerForm");

  if (registerForm) {
    registerForm.addEventListener("submit", function (e) {
      var pwd = passwordInput ? passwordInput.value : "";
      var confirm = confirmInput ? confirmInput.value : "";

      if (pwd !== confirm) {
        e.preventDefault();
        checkMatch(); // ensure hint is visible
        if (confirmInput) confirmInput.focus();
      }
    });
  }
})();
