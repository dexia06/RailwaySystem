import { useState } from "react";
import "./App.css";
function App() {
  const [login, setLogin] = useState(false);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  function handleLogin() {
    if (username === "dexia" && password === "dexi16") {
      setLogin(true);
    } else {
      alert("Invalid Username or Password");
    }
  }
  if (login) {
    return (
      <div className="dashboard">
        <h1>🚆   SmartRail</h1>
        <h2>Welcome, Admin!</h2>
        <div className="cards">
          <div>🚆<br />Train Status<br /><b>Normal</b></div>
          <div>🚧<br />Gate Status<br /><b>Closed</b></div>
          <div>⚠️<br />Alerts<br /><b>No Alerts</b></div>
          <div>🚦<br />Crossing Status<br /><b>Safe</b></div>
        </div>
        <button onClick={() => setLogin(false)}>Logout</button>
      </div>
    );
  }
  return (
    <div className="login-page">
      <div className="login-box">
        <h1>🚆    SmartRail</h1>
        <p>Railway Safety System</p>
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button onClick={handleLogin}>Login</button>
      </div>
    </div>
  );
}
export default App;