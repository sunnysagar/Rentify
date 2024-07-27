import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/authService";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const NavBar = () =>{
    const { isAuthenticated, logout} = useAuth();

    const handleLogout = () =>{
        logout();
        toast.success('Thank You! Visit Again!!');
    };
    
    return (
        <nav>
            <ul>
                {isAuthenticated ? (
                    <>
                        <li><Link to="/">Home</Link></li>
                        <li><Link to="/profile">Profile</Link></li>
                        <li><Link to="/contact">Contact Us</Link></li>
                        <li><Link to="/about">About Us</Link></li>
                        <li><button onClick={handleLogout}>Log out</button></li>
                    </>
                ) : (
                    <>
                        <h2>Rentify:Best place to see property.</h2>
                    </>
                )}
            </ul>
        </nav>
    );
};

export default NavBar;