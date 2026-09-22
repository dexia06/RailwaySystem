import { useState } from "react";

const API_BASE_URL = "https://railwaysystem-production.up.railway.app";
function Register({ onBack, onRegistered }) {
  const [step, setStep] = useState(1);

  const [email, setEmail] = useState("");
  const [otp, setOtp] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [role, setRole] = useState("OFFICER");
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  function isValidEmail(value) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
  }

  async function sendOtp() {
    const cleanEmail = email.trim();

    if (!cleanEmail) {
      setMessage("Please enter your email address");
      return;
    }

    if (!isValidEmail(cleanEmail)) {
      setMessage("Please enter a valid email ID");
      return;
    }

    setLoading(true);
    setMessage("");

    try {
      const response = await fetch(
        `${API_BASE_URL}/api/auth/register/send-otp`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            email: cleanEmail
          })
        }
      );

      const result = await response.text();

      if (result === "OTP sent successfully") {
        setMessage("OTP sent successfully. Check your email.");
        setStep(2);
      } else {
        setMessage(result);
      }
    } catch (error) {
      console.error(error);
      setMessage("Unable to connect to SmartRail backend");
    } finally {
      setLoading(false);
    }
  }

  async function verifyOtp() {
    if (otp.length !== 6) {
      setMessage("Please enter the 6-digit OTP");
      return;
    }

    setLoading(true);
    setMessage("");

    try {
      const response = await fetch(
        `${API_BASE_URL}/api/auth/register/verify-otp`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            email: email.trim(),
            otp: otp
          })
        }
      );

      const result = await response.text();

      if (result === "Email verified successfully") {
        setMessage("Email verified successfully");
        setStep(3);
      } else {
        setMessage(result);
      }
    } catch (error) {
      console.error(error);
      setMessage("Unable to connect to SmartRail backend");
    } finally {
      setLoading(false);
    }
  }

  async function createAccount() {
    if (!password) {
      setMessage("Please create a password");
      return;
    }

    if (!confirmPassword) {
      setMessage("Please confirm your password");
      return;
    }

    if (password !== confirmPassword) {
      setMessage("Passwords do not match");
      return;
    }

    if (password.length < 8) {
      setMessage("Password must contain at least 8 characters");
      return;
    }

    const passwordPattern =
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^a-zA-Z0-9]).{8,}$/;

    if (!passwordPattern.test(password)) {
      setMessage(
        "Password must contain uppercase, lowercase, number and special character"
      );
      return;
    }

    setLoading(true);
    setMessage("");

    try {
      const response = await fetch(
        `${API_BASE_URL}/api/auth/register/complete`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            name: email.split("@")[0],
            email: email.trim(),
            password: password,
            role: role
          })
        }
      );

      const result = await response.text();

      if (result === "Registration successful") {
        setStep(4);
        setMessage("Account created successfully!");
      } else {
        setMessage(result);
      }
    } catch (error) {
      console.error(error);
      setMessage("Unable to connect to SmartRail backend");
    } finally {
      setLoading(false);
    }
  }

  function goToSignIn() {
    if (onRegistered) {
      onRegistered(email.trim());
    } else {
      onBack();
    }
  }

  return (
    <div style={styles.container}>
      <div style={styles.card}>
        <h1 style={styles.title}>🚆 SmartRail</h1>

        {step === 1 && (
          <>
            <h2>Create Account</h2>

            <p style={styles.subtitle}>
              Enter your valid email ID
            </p>

            <input
              style={styles.input}
              type="email"
              placeholder="Enter Email ID"
              value={email}
              onChange={(e) => {
                setEmail(e.target.value);
                setMessage("");
              }}
            />

            <button
              style={styles.button}
              onClick={sendOtp}
              disabled={loading}
            >
              {loading ? "Sending OTP..." : "Continue"}
            </button>

            <button
              style={styles.backButton}
              onClick={onBack}
            >
              Back to Sign In
            </button>
          </>
        )}

        {step === 2 && (
          <>
            <h2>Verify Email</h2>

            <p style={styles.subtitle}>
              Enter the OTP sent to
            </p>

            <p style={styles.emailText}>
              {email}
            </p>

            <input
              style={styles.input}
              type="text"
              inputMode="numeric"
              maxLength="6"
              placeholder="Enter 6-digit OTP"
              value={otp}
              onChange={(e) => {
                setOtp(
                  e.target.value.replace(/\D/g, "")
                );
                setMessage("");
              }}
            />

            <button
              style={styles.button}
              onClick={verifyOtp}
              disabled={loading}
            >
              {loading ? "Verifying..." : "Verify OTP"}
            </button>

            <button
              style={styles.backButton}
              onClick={() => {
                setStep(1);
                setOtp("");
                setMessage("");
              }}
            >
              Change Email
            </button>
          </>
        )}

        {step === 3 && (
          <>
            <h2>Create Password</h2>

            <p style={styles.subtitle}>
              Create your account details
            </p>

            <select
              style={styles.input}
              value={role}
              onChange={(e) => {
                setRole(e.target.value);
                setMessage("");
              }}
            >
              <option value="OFFICER">
                Railway Officer
              </option>

              <option value="ADMIN">
                Admin
              </option>

              <option value="MAINTENANCE">
                Maintenance Staff
              </option>
            </select>

            <input
              style={styles.input}
              type="password"
              placeholder="Create Password"
              value={password}
              onChange={(e) => {
                setPassword(e.target.value);
                setMessage("");
              }}
            />

            <input
              style={styles.input}
              type="password"
              placeholder="Confirm Password"
              value={confirmPassword}
              onChange={(e) => {
                setConfirmPassword(e.target.value);
                setMessage("");
              }}
            />

            <div style={styles.rules}>
              <b>Password must contain:</b>

              <p>✓ Minimum 8 characters</p>
              <p>✓ One uppercase letter</p>
              <p>✓ One lowercase letter</p>
              <p>✓ One number</p>
              <p>✓ One special character</p>
            </div>

            <button
              style={styles.button}
              onClick={createAccount}
              disabled={loading}
            >
              {loading
                ? "Creating Account..."
                : "Create Account"}
            </button>
          </>
        )}

        {step === 4 && (
          <>
            <h2>🎉 Account Created</h2>

            <p style={styles.successText}>
              Your SmartRail account has been
              created successfully.
            </p>

            <p style={styles.subtitle}>
              Role:{" "}
              <b>
                {role === "OFFICER"
                  ? "Railway Officer"
                  : role === "ADMIN"
                  ? "Admin"
                  : "Maintenance Staff"}
              </b>
            </p>

            <p style={styles.subtitle}>
              You can now sign in using your email
              and password.
            </p>

            <button
              style={styles.button}
              onClick={goToSignIn}
            >
              Go to Sign In
            </button>
          </>
        )}

        {message && (
          <p
            style={
              message.includes("successfully")
                ? styles.successMessage
                : styles.message
            }
          >
            {message}
          </p>
        )}
      </div>
    </div>
  );
}

const styles = {
  container: {
    minHeight: "100vh",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    background: "#f4f6f8"
  },

  card: {
    width: "400px",
    padding: "35px",
    borderRadius: "15px",
    background: "white",
    boxShadow: "0 5px 20px rgba(0,0,0,0.15)",
    textAlign: "center"
  },

  title: {
    color: "#1e3a8a",
    marginBottom: "20px"
  },

  subtitle: {
    color: "#666",
    marginBottom: "15px"
  },

  emailText: {
    color: "#1e3a8a",
    fontWeight: "bold",
    marginBottom: "15px"
  },

  input: {
    width: "100%",
    padding: "12px",
    margin: "10px 0",
    border: "1px solid #ccc",
    borderRadius: "8px",
    boxSizing: "border-box",
    fontSize: "14px"
  },

  button: {
    width: "100%",
    padding: "12px",
    marginTop: "15px",
    border: "none",
    borderRadius: "8px",
    background: "#1e3a8a",
    color: "white",
    fontSize: "16px",
    cursor: "pointer"
  },

  backButton: {
    width: "100%",
    padding: "10px",
    marginTop: "10px",
    border: "none",
    background: "transparent",
    color: "#1e3a8a",
    cursor: "pointer"
  },

  rules: {
    textAlign: "left",
    fontSize: "14px",
    marginTop: "15px",
    color: "#555"
  },

  message: {
    marginTop: "20px",
    fontWeight: "bold",
    color: "#dc2626"
  },

  successMessage: {
    marginTop: "20px",
    fontWeight: "bold",
    color: "#16a34a"
  },

  successText: {
    color: "#16a34a",
    fontWeight: "bold"
  }
};

export default Register;