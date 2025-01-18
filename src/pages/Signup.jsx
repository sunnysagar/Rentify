import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../style.css';
import { useAuth } from '../context/authService';

const Signup = () => {
  const [user, setUser] = useState({
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    password: '',
    userType: 'Buyer' // default userType
  });

  const [error, setError] = useState('');
  const { signup } = useAuth();

  const handleSuccess = () => {
    toast.success('You have registered successfully!');
  };

  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUser((prevState) => ({
      ...prevState,
      [name]: value
    }));
  };

  const validatePassword = (password) => {
    const passwordRegex = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\S+$).{8,}$/;
    return passwordRegex.test(password);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validatePassword(user.password)) {
      setError(
        "Password must contain at least 8 characters, including at least one digit, one lowercase letter, one uppercase letter, one special character, and no whitespace."
      );
      return;
    }
    try {
      await signup(user);
      handleSuccess();
      navigate('/login');
    } catch (error) {
      console.error('Error signing up:', error);
      setError('An error occurred during sign up. Please try again.');
    }
  };

  return (
    <div className="form-container">
      <div className="formm">
        <div className="span">
          <div className="left">
            <h2>Signup</h2>
          </div>
        </div>
        <form onSubmit={handleSubmit}>
          <span className='name-field'>
            <div className="input-field">
              <label>First Name</label>
              <input
                type="text"
                name="firstName"
                required
                value={user.firstName}
                onChange={handleChange}
              />
            </div>
            <div className="input-field">
              <label>Last Name</label>
              <input
                type="text"
                name="lastName"
                required
                value={user.lastName}
                onChange={handleChange}
              />
            </div>
          </span>
          <div className="input-field">
            <label>Email</label>
            <input
              type="email"
              name="email"
              required
              value={user.email}
              onChange={handleChange}
            />
          </div>
          <div className="input-field">
            <label>Phone Number</label>
            <input
              type="text"
              name="phoneNumber"
              required
              value={user.phoneNumber}
              onChange={handleChange}
            />
          </div>
          <div className="input-field">
            <label>Password</label>
            <input
              type="password"
              name="password"
              required
              value={user.password}
              onChange={handleChange}
            />
          </div>
          <div className="input-field">
            <label>User Type:</label>
            <select name="userType" value={user.userType} onChange={handleChange}>
              <option value="Buyer">Buyer</option>
              <option value="Seller">Seller</option>
            </select>
          </div>
          <button type="submit">Sign Up</button>
          {error && <span className="error">{error}</span>}
          <div className="Log">
            <a href="/login">Already have an account? </a>
          </div>
          <ToastContainer />
        </form>
      </div>
    </div>
  );
};

export default Signup;
