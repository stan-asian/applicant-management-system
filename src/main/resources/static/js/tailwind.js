tailwind.config = {
  theme: {
    extend: {
      colors: {
        forest: "#254F22",
        ember: "#A03A13",
        flame: "#F5824A",
        cream: "#EDE4C2",
      },
      fontFamily: {
        display: ['"Playfair Display"', "serif"],
        body: ['"DM Sans"', "sans-serif"],
      },
      keyframes: {
        fadeDown: {
          "0%": { opacity: "0", transform: "translateY(-10px)" },
          "100%": { opacity: "1", transform: "translateY(0)" },
        },
        fadeUp: {
          "0%": { opacity: "0", transform: "translateY(20px)" },
          "100%": { opacity: "1", transform: "translateY(0)" },
        },
        float: {
          "0%, 100%": { transform: "translateY(0px)" },
          "50%": { transform: "translateY(-8px)" },
        },
      },
      animation: {
        "fade-down": "fadeDown 0.22s ease forwards",
        "fade-up-1": "fadeUp 0.6s ease 0.1s forwards",
        "fade-up-2": "fadeUp 0.6s ease 0.25s forwards",
        "fade-up-3": "fadeUp 0.6s ease 0.4s forwards",
        "fade-up-4": "fadeUp 0.6s ease 0.55s forwards",
        float: "float 4s ease-in-out infinite",
      },
    },
  },
};
