document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');
    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');
    const loginPage = document.getElementById('loginPage');
    const dashboard = document.getElementById('dashboard');
    const fieldData = document.getElementById('fieldData');
    const addFieldBtn = document.getElementById('addFieldBtn');
    const logoutBtn = document.getElementById('logoutBtn');
  
    // Simulated API endpoint
    const API_URL = 'http://localhost:8080/api';
  
    // Handle login
    loginForm.addEventListener('submit', async (e) => {
      e.preventDefault();
      const email = emailInput.value;
      const password = passwordInput.value;
  
      try {
        const response = await fetch(`${API_URL}/auth/login`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ email, password }),
        });
  
        if (!response.ok) throw new Error('Login failed');
        
        const data = await response.json();
        localStorage.setItem('token', data.token);
        showDashboard();
        loadFieldData();
      } catch (error) {
        alert(error.message);
      }
    });
  
    // Show dashboard
    function showDashboard() {
      loginPage.classList.add('d-none');
      dashboard.classList.remove('d-none');
    }
  
    // Load field data
    async function loadFieldData() {
      try {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_URL}/fields`, {
          headers: { 'Authorization': `Bearer ${token}` },
        });
        if (!response.ok) throw new Error('Failed to fetch field data');
  
        const fields = await response.json();
        fieldData.innerHTML = fields.map(field => `
          <div class="card mt-3">
            <div class="card-body">
              <h5>${field.name}</h5>
              <p>Location: ${field.location}</p>
              <p>Size: ${field.size} sq.m</p>
            </div>
          </div>
        `).join('');
      } catch (error) {
        alert(error.message);
      }
    }
  
    // Add new field
    addFieldBtn.addEventListener('click', async () => {
      const fieldName = prompt('Enter field name:');
      const fieldLocation = prompt('Enter field location:');
      const fieldSize = prompt('Enter field size (sq.m):');
  
      if (fieldName && fieldLocation && fieldSize) {
        try {
          const token = localStorage.getItem('token');
          const response = await fetch(`${API_URL}/fields`, {
            method: 'POST',
            headers: { 
              'Content-Type': 'application/json',
              'Authorization': `Bearer ${token}`,
            },
            body: JSON.stringify({ name: fieldName, location: fieldLocation, size: fieldSize }),
          });
  
          if (!response.ok) throw new Error('Failed to add field');
          loadFieldData();
        } catch (error) {
          alert(error.message);
        }
      }
    });
  
    // Logout functionality
    logoutBtn.addEventListener('click', () => {
      localStorage.removeItem('token');
      dashboard.classList.add('d-none');
      loginPage.classList.remove('d-none');
    });
  
    // Check if logged in on load
    if (localStorage.getItem('token')) {
      showDashboard();
      loadFieldData();
    }
  });
  