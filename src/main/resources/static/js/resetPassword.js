/**
 * reset-password.js
 * UI validation for the ApplicaTrack Reset Password page.
 */

(function () {
  "use strict";

  /* ─── DOM references ─────────────────────────────────────── */
  const newPwdInput = document.getElementById("newPassword");
  const confirmInput = document.getElementById("confirmPassword");
  const submitBtn = document.getElementById("submitBtn");
  const matchBanner = document.getElementById("matchBanner");
  const confirmFb = document.getElementById("confirmFeedback");
  const strengthLabel = document.getElementById("strengthLabel");
  const segments = [0, 1, 2, 3].map((i) => document.getElementById("seg" + i));
  const form = document.getElementById("resetForm");
  const successState = document.getElementById("successState");

  /* ─── SVG icons (reusable strings) ─────────────────────────── */
  const iconCheck = `<svg class="req-icon" viewBox="0 0 24 24" fill="none"
    stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
    <circle cx="12" cy="12" r="10"/>
    <polyline points="9 12 11 14 15 10"/>
  </svg>`;

  const iconDot = `<svg class="req-icon" viewBox="0 0 24 24" fill="none"
    stroke="currentColor" stroke-width="2.2" stroke-linecap="round" stroke-linejoin="round">
    <circle cx="12" cy="12" r="10"/>
    <line x1="8" y1="12" x2="12" y2="12"/>
  </svg>`;

  const iconX = `<svg width="13" height="13" viewBox="0 0 24 24" fill="none"
    stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
    <circle cx="12" cy="12" r="10"/>
    <line x1="15" y1="9" x2="9" y2="15"/>
    <line x1="9" y1="9" x2="15" y2="15"/>
  </svg>`;

  const iconCheckSm = `<svg width="13" height="13" viewBox="0 0 24 24" fill="none"
    stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
    <circle cx="12" cy="12" r="10"/>
    <polyline points="9 12 11 14 15 10"/>
  </svg>`;

  /* ─── Password rules ─────────────────────────────────────── */
  const RULES = {
    length: (v) => v.length >= 8,
    upper: (v) => /[A-Z]/.test(v),
    lower: (v) => /[a-z]/.test(v),
    number: (v) => /[0-9]/.test(v),
    special: (v) => /[^A-Za-z0-9]/.test(v),
  };

  /* ─── Strength config ────────────────────────────────────── */
  const STRENGTH_LEVELS = [
    { label: "", segClass: "", segs: 0 },
    { label: "Weak", segClass: "seg-weak", segs: 1 },
    { label: "Fair", segClass: "seg-fair", segs: 2 },
    { label: "Good", segClass: "seg-good", segs: 3 },
    { label: "Strong", segClass: "seg-strong", segs: 4 },
  ];

  /* ─── Evaluate strength ──────────────────────────────────── */
  function getStrength(password) {
    if (!password) return 0;
    let score = 0;
    if (RULES.length(password)) score++;
    if (RULES.upper(password)) score++;
    if (RULES.lower(password)) score++;
    if (RULES.number(password)) score++;
    if (RULES.special(password)) score++;
    // Map 5-point score → 4 levels
    if (score <= 1) return 1;
    if (score === 2) return 2;
    if (score <= 4) return 3;
    return 4;
  }

  /* ─── Update strength bar ────────────────────────────────── */
  function updateStrengthBar(password) {
    const level = password ? getStrength(password) : 0;
    const cfg = STRENGTH_LEVELS[level];

    segments.forEach((seg, i) => {
      seg.className = "strength-segment";
      if (i < cfg.segs) seg.classList.add(cfg.segClass);
    });

    strengthLabel.textContent = cfg.label;
    strengthLabel.style.color =
      level === 0
        ? "rgba(37,79,34,0.4)"
        : level === 1
          ? "#a03a13"
          : level === 2
            ? "#c87137"
            : level === 3
              ? "#8aa63a"
              : "#254f22";
  }

  /* ─── Update requirements list ───────────────────────────── */
  function updateRequirements(password) {
    Object.entries(RULES).forEach(([rule, fn]) => {
      const li = document.getElementById("req-" + rule);
      const met = fn(password);
      li.classList.toggle("met", met);
      const svgEl = li.querySelector("svg");
      if (svgEl) svgEl.outerHTML = met ? iconCheck : iconDot;
    });
  }

  /* ─── Check all requirements met ────────────────────────── */
  function allRequirementsMet(password) {
    return Object.values(RULES).every((fn) => fn(password));
  }

  /* ─── Update confirm field feedback ─────────────────────── */
  function updateConfirmFeedback() {
    const pwd = newPwdInput.value;
    const confirm = confirmInput.value;

    if (!confirm) {
      confirmFb.className = "field-feedback";
      confirmFb.innerHTML = "";
      confirmInput.classList.remove("is-valid", "is-error");
      return false;
    }

    const matches = pwd === confirm;
    confirmInput.classList.toggle("is-valid", matches);
    confirmInput.classList.toggle("is-error", !matches);

    confirmFb.className =
      "field-feedback " + (matches ? "show-success" : "show-error");
    confirmFb.innerHTML = matches
      ? iconCheckSm + " Passwords match"
      : iconX + " Passwords do not match";

    return matches;
  }

  /* ─── Update match banner ─────────────────────────────────── */
  function updateMatchBanner(pwdReady, matches, confirmHasValue) {
    if (!confirmHasValue) {
      matchBanner.className = "match-banner";
      matchBanner.innerHTML = "";
      return;
    }

    if (matches && pwdReady) {
      matchBanner.className = "match-banner match-ok visible";
      matchBanner.innerHTML = `${iconCheckSm} <span>Great — your passwords match and meet all requirements.</span>`;
    } else if (!matches) {
      matchBanner.className = "match-banner match-fail visible";
      matchBanner.innerHTML = `${iconX} <span>The passwords don't match yet. Please check and try again.</span>`;
    } else {
      matchBanner.className = "match-banner match-fail visible";
      matchBanner.innerHTML = `${iconX} <span>Your password doesn't meet all the requirements above.</span>`;
    }
  }

  /* ─── Master validation runner ───────────────────────────── */
  function validate() {
    const pwd = newPwdInput.value;
    const confirm = confirmInput.value;
    const pwdReady = allRequirementsMet(pwd);
    const matches = pwd === confirm && confirm.length > 0;
    const confirmHasValue = confirm.length > 0;

    newPwdInput.classList.toggle("is-valid", pwdReady && pwd.length > 0);
    newPwdInput.classList.toggle("is-error", !pwdReady && pwd.length > 0);

    updateStrengthBar(pwd);
    updateRequirements(pwd);
    const confirmMatch = updateConfirmFeedback();
    updateMatchBanner(pwdReady, confirmMatch, confirmHasValue);

    submitBtn.disabled = !(pwdReady && matches);
  }

  /* ─── Toggle password visibility ─────────────────────────── */
  document.querySelectorAll(".toggle-btn").forEach((btn) => {
    btn.addEventListener("click", () => {
      const targetId = btn.dataset.target;
      const input = document.getElementById(targetId);
      const showIcon = btn.querySelector(".icon-show");
      const hideIcon = btn.querySelector(".icon-hide");

      if (input.type === "password") {
        input.type = "text";
        showIcon.style.display = "none";
        hideIcon.style.display = "";
        btn.setAttribute("aria-label", "Hide password");
      } else {
        input.type = "password";
        showIcon.style.display = "";
        hideIcon.style.display = "none";
        btn.setAttribute("aria-label", "Show password");
      }
    });
  });

  /* ─── Event listeners ────────────────────────────────────── */
  newPwdInput.addEventListener("input", validate);
  confirmInput.addEventListener("input", validate);

  /* ─── Form submit ─────────────────────────────────────────── */
  form.addEventListener("submit", function (e) {
    // Final guard (button should already be disabled if invalid)
    if (submitBtn.disabled) {
      e.preventDefault();
      return;
    }

    /**
     * Uncomment the block below to wire up real form submission via fetch.
     * Otherwise the default HTML form POST will fire.
     *
     * e.preventDefault();
     * const data = new FormData(form);
     * fetch(form.action, { method: "POST", body: data })
     *   .then(res => {
     *     if (res.ok) showSuccess();
     *     else throw new Error("Server error");
     *   })
     *   .catch(err => console.error(err));
     */
  });

  /* ─── Show success panel (call after successful server response) ── */
  function showSuccess() {
    form.style.display = "none";
    successState.style.display = "block";
  }

  // Expose for external use (e.g. fetch callback)
  window.showResetSuccess = showSuccess;
})();
