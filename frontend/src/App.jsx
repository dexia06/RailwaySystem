import { useState } from "react";
import "./App.css";
import Register from "./Register";

const API_BASE_URL = "https://railwaysystem-production.up.railway.app";
function App() {
  const [login, setLogin] = useState(false);
  const [showRegister, setShowRegister] = useState(false);

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("");

  const [crossing, setCrossing] = useState(null);
  const [loading, setLoading] = useState(false);

  const [adminDetail, setAdminDetail] = useState("");
  const [officerDetail, setOfficerDetail] = useState("");
  const [maintenanceDetail, setMaintenanceDetail] = useState("");

  async function handleLogin() {
    if (!username || !password) {
      alert("Please enter email and password");
      return;
    }

    try {
      const response = await fetch(
        `${API_BASE_URL}/api/auth/login`,
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
        alert("Invalid email or password");
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
        `${API_BASE_URL}/api/safety/detect/1/${distance}`,
        {
          method: "POST"
        }
      );

      if (!response.ok) {
        throw new Error("Safety API failed");
      }

      const data = await response.json();

      setCrossing({
        crossingName:
          data.crossingCode || "LC-TPJ-01",

        location:
          data.location || "Tiruchirappalli Junction",

        trainStatus:
          data.trainStatus ||
          (distance === 2
            ? "TRAIN DETECTED - 2 KM"
            : "TRAIN DETECTED - 1 KM"),

        gateStatus:
          data.gateStatus ||
          (data.crossingStatus === "GATE_NOT_CLOSED"
            ? "OPEN"
            : "CLOSED"),

        occupancyStatus:
          data.occupancyStatus || "CLEAR",

        safetyStatus:
          data.safetyStatus ||
          (data.crossingStatus === "GATE_NOT_CLOSED"
            ? "CRITICAL"
            : distance === 2
            ? "WARNING"
            : "SAFE")
      });
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
        `${API_BASE_URL}/api/gate/close/1`,
        {
          method: "POST"
        }
      );

      if (!response.ok) {
        throw new Error("Gate control failed");
      }

      const data = await response.json();

      setCrossing((previous) => ({
        ...previous,
        ...data,
        gateStatus: "CLOSED"
      }));
    } catch (error) {
      console.error(error);
      alert("Unable to close gate");
    }

    setLoading(false);
  }

  function logout() {
    setLogin(false);
    setUsername("");
    setPassword("");
    setRole("");
    setCrossing(null);

    setAdminDetail("");
    setOfficerDetail("");
    setMaintenanceDetail("");
  }

  /* =========================
     ADMIN DASHBOARD
     ========================= */

  function AdminDashboard() {
    return (
      <div className="dashboard">
        <h1>🚆 SmartRail</h1>

        <h2>👑 Admin Dashboard</h2>

        <div className="cards">

          <div
            onClick={() => setAdminDetail("train")}
            style={{ cursor: "pointer" }}
          >
            🚆
            <br />
            Train Status
            <br />
            <b>Normal</b>
          </div>

          <div
            onClick={() => setAdminDetail("gate")}
            style={{ cursor: "pointer" }}
          >
            🚧
            <br />
            Gate Status
            <br />
            <b>Closed</b>
          </div>

          <div
            onClick={() => setAdminDetail("alerts")}
            style={{ cursor: "pointer" }}
          >
            ⚠️
            <br />
            Alerts
            <br />
            <b>No Alerts</b>
          </div>

          <div
            onClick={() => setAdminDetail("crossing")}
            style={{ cursor: "pointer" }}
          >
            🚦
            <br />
            Crossing Status
            <br />
            <b>Safe</b>
          </div>

        </div>

        {adminDetail === "train" && (
          <div>
            <h3>🚆 Train Status Details</h3>

            <p>
              <b>Detection Status:</b> Monitoring Active
            </p>

            <p>
              <b>First Detection Point:</b> 2 KM
            </p>

            <p>
              <b>Verification Point:</b> 1 KM
            </p>

            <p>
              <b>Train Monitoring:</b> Active
            </p>

            <button onClick={() => setAdminDetail("")}>
              Close
            </button>
          </div>
        )}

        {adminDetail === "gate" && (
          <div>
            <h3>🚧 Gate Status Details</h3>

            <p>
              <b>Gate Control:</b> Automatic
            </p>

            <p>
              <b>Current Gate Status:</b> Closed
            </p>

            <p>
              <b>Gate Monitoring:</b> Active
            </p>

            <p>
              <b>Safety Verification:</b> Enabled
            </p>

            <button onClick={() => setAdminDetail("")}>
              Close
            </button>
          </div>
        )}

        {adminDetail === "alerts" && (
          <div>
            <h3>⚠️ Alert Details</h3>

            <p>
              <b>Active Alerts:</b> 0
            </p>

            <p>
              <b>Occupancy Alert:</b> None
            </p>

            <p>
              <b>Gate Failure Alert:</b> None
            </p>

            <p>
              <b>Officer Notification:</b> Not Required
            </p>

            <button onClick={() => setAdminDetail("")}>
              Close
            </button>
          </div>
        )}

        {adminDetail === "crossing" && (
          <div>
            <h3>🚦 Crossing Status Details</h3>

            <p>
              <b>Crossing:</b> LC-TPJ-01
            </p>

            <p>
              <b>Location:</b> Tiruchirappalli Junction
            </p>

            <p>
              <b>Occupancy:</b> Clear
            </p>

            <p>
              <b>Gate Status:</b> Closed
            </p>

            <p>
              <b>Safety Status:</b> Safe
            </p>

            <button onClick={() => setAdminDetail("")}>
              Close
            </button>
          </div>
        )}

        <h3>System Administration</h3>

        <p>
          Manage railway crossings, users and overall
          SmartRail operations.
        </p>

        <button onClick={logout}>
          Logout
        </button>
      </div>
    );
  }

  /* =========================
     OFFICER DASHBOARD
     ========================= */

  function OfficerDashboard() {
    return (
      <div className="dashboard">
        <h1>🚆 SmartRail</h1>

        <h2>🚦 Railway Officer Dashboard</h2>

        <div className="cards">

          <div
            onClick={() => setOfficerDetail("train")}
            style={{ cursor: "pointer" }}
          >
            🚆
            <br />
            Train Detection
            <br />
            <b>
              {crossing
                ? crossing.trainStatus || "Not Detected"
                : "Monitoring"}
            </b>
          </div>

          <div
            onClick={() => setOfficerDetail("gate")}
            style={{ cursor: "pointer" }}
          >
            🚧
            <br />
            Gate Status
            <br />
            <b>
              {crossing
                ? crossing.gateStatus || "OPEN"
                : "Closed"}
            </b>
          </div>

          <div
            onClick={() => setOfficerDetail("alerts")}
            style={{ cursor: "pointer" }}
          >
            ⚠️
            <br />
            Alerts
            <br />
            <b>
              {crossing
                ? crossing.safetyStatus === "CRITICAL"
                  ? "CRITICAL"
                  : crossing.safetyStatus === "WARNING"
                  ? "WARNING"
                  : "No Alerts"
                : "No Alerts"}
            </b>
          </div>

          <div
            onClick={() => setOfficerDetail("safety")}
            style={{ cursor: "pointer" }}
          >
            🚦
            <br />
            Safety Status
            <br />
            <b>
              {crossing
                ? crossing.safetyStatus || "SAFE"
                : "Safe"}
            </b>
          </div>

        </div>

        {officerDetail === "train" && (
          <div>
            <h3>🚆 Train Detection Details</h3>

            <p>
              <b>Detection Status:</b>{" "}
              {crossing
                ? crossing.trainStatus || "Train Not Detected"
                : "Monitoring Active"}
            </p>

            <p>
              <b>First Detection Point:</b> 2 KM
            </p>

            <p>
              <b>Verification Point:</b> 1 KM
            </p>

            <p>
              <b>Detection System:</b> Active
            </p>

            <button onClick={() => setOfficerDetail("")}>
              Close
            </button>
          </div>
        )}

        {officerDetail === "gate" && (
          <div>
            <h3>🚧 Gate Status Details</h3>

            <p>
              <b>Gate Control:</b> Automatic
            </p>

            <p>
              <b>Current Status:</b>{" "}
              {crossing
                ? crossing.gateStatus || "OPEN"
                : "Closed"}
            </p>

            <p>
              <b>Gate Monitoring:</b> Active
            </p>

            <p>
              <b>Gate Verification:</b> Enabled
            </p>

            <button onClick={() => setOfficerDetail("")}>
              Close
            </button>
          </div>
        )}

        {officerDetail === "alerts" && (
          <div>
            <h3>⚠️ Alert Details</h3>

            <p>
              <b>Current Alert:</b>{" "}
              {crossing
                ? crossing.safetyStatus === "CRITICAL"
                  ? "Critical Alert"
                  : crossing.safetyStatus === "WARNING"
                  ? "Warning"
                  : "No Active Alerts"
                : "No Active Alerts"}
            </p>

            <p>
              <b>Occupancy Alert:</b>{" "}
              {crossing?.occupancyStatus === "OCCUPIED"
                ? "Crossing Occupied"
                : "None"}
            </p>

            <p>
              <b>Gate Failure Alert:</b>{" "}
              {crossing?.gateStatus === "OPEN"
                ? "Gate Not Closed"
                : "None"}
            </p>

            <p>
              <b>Officer Notification:</b> Active
            </p>

            <button onClick={() => setOfficerDetail("")}>
              Close
            </button>
          </div>
        )}

        {officerDetail === "safety" && (
          <div>
            <h3>🚦 Safety Status Details</h3>

            <p>
              <b>Crossing:</b>{" "}
              {crossing?.crossingName || "LC-TPJ-01"}
            </p>

            <p>
              <b>Location:</b>{" "}
              {crossing?.location ||
                "Tiruchirappalli Junction"}
            </p>

            <p>
              <b>Occupancy:</b>{" "}
              {crossing?.occupancyStatus || "CLEAR"}
            </p>

            <p>
              <b>Gate:</b>{" "}
              {crossing?.gateStatus || "CLOSED"}
            </p>

            <p>
              <b>Overall Safety:</b>{" "}
              {crossing?.safetyStatus || "SAFE"}
            </p>

            <button onClick={() => setOfficerDetail("")}>
              Close
            </button>
          </div>
        )}

        <h3>Railway Safety Monitoring</h3>

        <p>
          Monitor train detection, crossing occupancy and
          gate safety.
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

        {loading && (
          <p>
            ⏳ Processing SmartRail safety request...
          </p>
        )}

        {crossing && (
          <div>
            <h3>Live Crossing Status</h3>

            <p>
              <b>Crossing:</b>{" "}
              {crossing.crossingName || "LC-TPJ-01"}
            </p>

            <p>
              <b>Location:</b>{" "}
              {crossing.location ||
                "Tiruchirappalli Junction"}
            </p>

            <p>
              <b>Train:</b>{" "}
              {crossing.trainStatus || "Not Detected"}
            </p>

            <p>
              <b>Gate:</b>{" "}
              {crossing.gateStatus || "OPEN"}
            </p>

            <p>
              <b>Occupancy:</b>{" "}
              {crossing.occupancyStatus || "CLEAR"}
            </p>

            <p>
              <b>Safety:</b>{" "}
              {crossing.safetyStatus || "SAFE"}
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

  /* =========================
     MAINTENANCE DASHBOARD
     ========================= */

  function MaintenanceDashboard() {
    return (
      <div className="dashboard">
        <h1>🚆 SmartRail</h1>

        <h2>🔧 Maintenance Dashboard</h2>

        <div className="cards">

          <div
            onClick={() => setMaintenanceDetail("gate")}
            style={{ cursor: "pointer" }}
          >
            🚧
            <br />
            Gate Equipment
            <br />
            <b>Operational</b>
          </div>

          <div
            onClick={() => setMaintenanceDetail("maintenance")}
            style={{ cursor: "pointer" }}
          >
            🔧
            <br />
            Maintenance
            <br />
            <b>No Pending Issues</b>
          </div>

          <div
            onClick={() => setMaintenanceDetail("signals")}
            style={{ cursor: "pointer" }}
          >
            🚦
            <br />
            Signals
            <br />
            <b>Operational</b>
          </div>

          <div
            onClick={() => setMaintenanceDetail("alerts")}
            style={{ cursor: "pointer" }}
          >
            ⚠️
            <br />
            Fault Alerts
            <br />
            <b>None</b>
          </div>

        </div>

        {maintenanceDetail === "gate" && (
          <div>
            <h3>🚧 Gate Equipment Details</h3>

            <p>
              <b>Gate System:</b> Automatic
            </p>

            <p>
              <b>Equipment Status:</b> Operational
            </p>

            <p>
              <b>Motor Status:</b> Normal
            </p>

            <p>
              <b>Gate Monitoring:</b> Active
            </p>

            <button
              onClick={() => setMaintenanceDetail("")}
            >
              Close
            </button>
          </div>
        )}

        {maintenanceDetail === "maintenance" && (
          <div>
            <h3>🔧 Maintenance Details</h3>

            <p>
              <b>Pending Issues:</b> None
            </p>

            <p>
              <b>Last Maintenance:</b> Completed
            </p>

            <p>
              <b>System Condition:</b> Normal
            </p>

            <p>
              <b>Maintenance Monitoring:</b> Active
            </p>

            <button
              onClick={() => setMaintenanceDetail("")}
            >
              Close
            </button>
          </div>
        )}

        {maintenanceDetail === "signals" && (
          <div>
            <h3>🚦 Signal System Details</h3>

            <p>
              <b>Signal Status:</b> Operational
            </p>

            <p>
              <b>Train Detection Signal:</b> Active
            </p>

            <p>
              <b>Communication:</b> Connected
            </p>

            <p>
              <b>Signal Monitoring:</b> Active
            </p>

            <button
              onClick={() => setMaintenanceDetail("")}
            >
              Close
            </button>
          </div>
        )}

        {maintenanceDetail === "alerts" && (
          <div>
            <h3>⚠️ Fault Alert Details</h3>

            <p>
              <b>Active Faults:</b> 0
            </p>

            <p>
              <b>Gate Fault:</b> None
            </p>

            <p>
              <b>Signal Fault:</b> None
            </p>

            <p>
              <b>Equipment Warning:</b> None
            </p>

            <button
              onClick={() => setMaintenanceDetail("")}
            >
              Close
            </button>
          </div>
        )}

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

  /* =========================
     REGISTER PAGE
     ========================= */

  if (showRegister) {
    return (
      <Register
        onBack={() => setShowRegister(false)}
        onRegistered={(registeredEmail) => {
          setUsername(registeredEmail);
          setPassword("");
          setShowRegister(false);
        }}
      />
    );
  }

  /* =========================
     DASHBOARD ROUTING
     ========================= */

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

  /* =========================
     SIGN IN PAGE
     ========================= */

  return (
    <div className="login-page">

      <div className="login-box">

        <h1>🚆 SmartRail</h1>

        <p>Railway Safety System</p>

        <input
          type="email"
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

        <button
          onClick={handleLogin}
          style={{
            width: "100%",
            marginTop: "15px"
          }}
        >
          Sign In
        </button>

        <button
          onClick={() => setShowRegister(true)}
          style={{
            width: "100%",
            marginTop: "10px",
            background: "white",
            color: "#1e3a8a",
            border: "1px solid #1e3a8a"
          }}
        >
          Register
        </button>

      </div>

    </div>
  );
}

export default App;