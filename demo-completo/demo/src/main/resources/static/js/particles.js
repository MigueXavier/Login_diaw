
document.addEventListener("DOMContentLoaded", function () {
  const container = document.getElementById("particles-container");
  if (!container) return;

  const particleCount = 18;
  for (let i = 0; i < particleCount; i++) {
    const particle = document.createElement("div");
    particle.classList.add("particle");

    const size = Math.random() * 4 + 3;
    particle.style.width = `${size}px`;
    particle.style.height = `${size}px`;
    particle.style.left = `${Math.random() * 100}vw`;

    const duration = Math.random() * 10 + 8;
    particle.style.animationDuration = `${duration}s`;
    particle.style.animationDelay = `${Math.random() * 10}s`;

    container.appendChild(particle);
  }
});
