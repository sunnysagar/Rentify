import React, { useState, useEffect} from "react";
import axios from "axios";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useNavigate } from "react-router-dom";
import '../style.css';
import { useAuth } from "../context/authService";

const Login = () =>{
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const { login } = useAuth();

    const handleSuccess = () =>{
        toast.success('You have looged in successfully!');
    }

    const handleSubmit = async (e) =>{
        e.preventDefault();
        try {
           await login(email, password);
           handleSuccess();
        } catch (error) {
            console.error('Error signing up:', error);
            setError('Invalid email or password')
            
        }
    };

    return (
        <div className='form-container'>
          <div className='formm'>
            <div className='span'>
              <div className='left'>
                <h2>Sign in</h2>
              </div>
              <div className='right'>
                <h3> <a href='/users/signup'>Create Account</a></h3>
              </div>
            
            </div>
            <form onSubmit={handleSubmit}>
    
              <div className='input-field'>
                  <label>Email</label>
                  <input type="email" 
                         value={email} 
                         placeholder="Your Email" 
                         required
                         onChange={(e) => setEmail(e.target.value)} />   
              </div>
    
              <div className='input-field'>
                  <label>Password</label>
                  <input type="password" 
                         value={password} 
                         placeholder="Enter password" 
                         required
                         onChange={(e) => setPassword(e.target.value)} />   
              </div>
    
              <button type="submit">Sign In</button>
              {error && <span className='error'>{error}</span>} 
                 <ToastContainer/>
              </form>
             
            </div>      
        </div>
      );


};

export default Login;