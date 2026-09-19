import { useState } from "react";
import "./App.css";

function App() {
  const [login, setLogin] = useState(false);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("");

  const [crossing, setCrossing] = useState(null);
  const [loading, setLoading] = useState(false);

  async function handleLogin() {
    if (!username || !password) {
      alert("Please enter username and password");
      return;
    }

    try {
      const response = await fetch(
        "http://localhost:8080/api/auth/login",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            email: username,
            password: password
          })
        }
      );

      const result = await response.text();

      if (response.ok && result.startsWith("Login successful")) {
        const userRole = result.split("Role: ")[1];

        setRole(userRole);
        setLogin(true);
      } else {
        alert("Invalid Username or Password");
      }
    } catch (error) {
      console.error(error);
      alert("Unable to connect to SmartRail backend");
    }
  }

  async function detectTrain(distance) {
    setLoading(true);

    try {
      const response = await fetch(
        `http://localhost:8080/api/safety/detect/1/${distance}`,
        {
          method: "POST"
        }
      );

      if (!response.ok) {
        throw new Error("Safety API failed");
      }

      const data = await response.json();

      setCrossing(data);
    } catch (error) {
      console.error(error);
      alert("Unable to connect to SmartRail safety system");
    }

    setLoading(false);
  }

  async function closeGate() {
    setLoading(true);

    try {
      const response = await fetch(
        "http://localhost:8080/api/gate/close/1",
        {
          method: "POST"
        }
      );

      if (!response.ok) {
        throw new Error("Gate control failed");
      }

      const data = await response.json();

      setCrossing(data);
    } catch (error) {
      console.error(error);
      alert("Unable to close gate");
    }

    setLoading(false);
  }

  function AdminDashboard() {
    return (
      <div className="dashboard">
        <h1>🚆 SmartRail</h1>

        <h2>👑 Admin Dashboard</h2>

        <div className="cards">
          <div>
            🚆
            <br />
            Train Status
            <br />
            <b>Normal</b>
          </div>

          <div>
            🚧
            <br />
            Gate Status
            <br />
            <b>Closed</b>
          </div>

          <div>
            ⚠️
            <br />
            Alerts
            <br />
            <b>No Alerts</b>
          </div>

          <div>
            🚦
            <br />
            Crossing Status
            <br />
            <b>Safe</b>
          </div>
        </div>

        <h3>System Administration</h3>

        <p>
          Manage railway crossings, users and overall SmartRail operations.
        </p>

        <button onClick={logout}>Logout</button>
      </div>
    );
  }

  function OfficerDashboard() {
    return (
      <div className="dashboard">
        <h1>🚆 SmartRail</h1>

        <h2>🚦 Railway Officer Dashboard</h2>

        <div className="cards">
          <div>
            🚆
            <br />
            Train Detection
            <br />
            <b>
              {crossing
                ? crossing.trainStatus
                : "Monitoring"}
            </b>
          </div>

          <div>
            🚧
            <br />
            Gate Status
            <br />
            <b>
              {crossing
                ? crossing.gateStatus
                : "Closed"}
            </b>
          </div>

          <div>
            ⚠️
            <br />
            Alerts
            <br />
            <b>
              {crossing
                ? crossing.safetyStatus === "CRITICAL"
                  ? "CRITICAL"
                  : "No Alerts"
                : "No Alerts"}
            </b>
          </div>

          <div>
            🚦
            <br />
            Safety Status
            <br />
            <b>
              {crossing
                ? crossing.safetyStatus
                : "Safe"}
            </b>
          </div>
        </div>

        <h3>Railway Safety Monitoring</h3>

        <p>
          Monitor train detection, crossing occupancy and gate safety.
        </p>

        <div>
          <button
            onClick={() => detectTrain(2)}
            disabled={loading}
          >
            🚆 Detect Train at 2 KM
          </button>

          <button
            onClick={closeGate}
            disabled={loading}
          >
            🚧 Close Gate
          </button>

          <button
            onClick={() => detectTrain(1)}
            disabled={loading}
          >
            ⚠️ Verify Train at 1 KM
          </button>
        </div>

        {crossing && (
          <div>
            <h3>Live Crossing Status</h3>

            <p>
              <b>Crossing:</b>{" "}
              {crossing.crossingName}
            </p>

            <p>
              <b>Location:</b>{" "}
              {crossing.location}
            </p>

            <p>
              <b>Train:</b>{" "}
              {crossing.trainStatus}
            </p>

            <p>
              <b>Gate:</b>{" "}
              {crossing.gateStatus}
            </p>

            <p>
              <b>Occupancy:</b>{" "}
              {crossing.occupancyStatus}
            </p>

            <p>
              <b>Safety:</b>{" "}
              {crossing.safetyStatus}
            </p>

            {crossing.safetyStatus === "CRITICAL" && (
              <h2>
                🚨 CRITICAL ALERT - Gate Not Closed
              </h2>
            )}

            {crossing.safetyStatus === "SAFE" && (
              <h2>
                ✅ CROSSING SAFE
              </h2>
            )}

            {crossing.safetyStatus === "WARNING" && (
              <h2>
                ⚠️ WARNING - Gate Closing
              </h2>
            )}
          </div>
        )}

        <button onClick={logout}>
          Logout
        </button>
      </div>
    );
  }

  function MaintenanceDashboard() {
    return (
      <div className="dashboard">
        <h1>🚆 SmartRail</h1>

        <h2>🔧 Maintenance Dashboard</h2>

        <div className="cards">
          <div>
            🚧
            <br />
            Gate Equipment
            <br />
            <b>Operational</b>
          </div>

          <div>
            🔧
            <br />
            Maintenance
            <br />
            <b>No Pending Issues</b>
          </div>

          <div>
            🚦
            <br />
            Signals
            <br />
            <b>Operational</b>
          </div>

          <div>
            ⚠️
            <br />
            Fault Alerts
            <br />
            <b>None</b>
          </div>
        </div>

        <h3>Maintenance Monitoring</h3>

        <p>
          Monitor gate equipment, signals and maintenance issues.
        </p>

        <button onClick={logout}>
          Logout
        </button>
      </div>
    );
  }

  function logout() {
    setLogin(false);
    setUsername("");
    setPassword("");
    setRole("");
    setCrossing(null);
  }

  if (login) {
    if (role === "ADMIN") {
      return <AdminDashboard />;
    }

    if (role === "OFFICER") {
      return <OfficerDashboard />;
    }

    if (role === "MAINTENANCE") {
      return <MaintenanceDashboard />;
    }

    return (
      <div className="dashboard">
        <h2>Unknown Role</h2>

        <button onClick={logout}>
          Logout
        </button>
      </div>
    );
  }

  return (
    <div className="login-page">
      <div className="login-box">
        <h1>🚆 SmartRail</h1>

        <p>Railway Safety System</p>

        <input
          type="text"
          placeholder="Email"
          value={username}
          onChange={(e) =>
            setUsername(e.target.value)
          }
        />

        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) =>
            setPassword(e.target.value)
          }
        />

        <button onClick={handleLogin}>
          Login
        </button>
      </div>
    </div>
  );
}

export default App;