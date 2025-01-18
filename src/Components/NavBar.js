import React, { useState } from "react";
import { Link } from "react-router-dom";
import { useAuth } from "../context/authService";
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './NavBar.css'
import '@fortawesome/fontawesome-free/css/all.css';

const NavBar = () =>{
    const { isAuthenticated, logout} = useAuth();
    const [isOpen, setOpen] = useState(false);

    const handleLogout = () =>{
        logout();
        toast.success('Thank You! Visit Again!!');
    };

    const toggleMenu = () =>{
        setOpen(!isOpen);
    }
    
    return (
        <nav>
            <div className="nav-container">
                <div className= {`nav-links ${isOpen ? `open` : ''}`}>
                <ul>
                    {isAuthenticated ? (
                        <>
                            <li ><Link to="/" onClick={() => setOpen(false)}>Home</Link></li>
                            <li ><Link to="/profile" onClick={() => setOpen(false)}>Profile</Link></li>
                            <li ><Link to="/contact" onClick={() => setOpen(false)}>Contact Us</Link></li>
                            <li ><Link to="/about" onClick={() => setOpen(false)}>About Us</Link></li>
                            <li ><button className="LogoutBtn" onClick={ () => {handleLogout(); setOpen(false);}}>Logout</button></li>
                        </>
                    ) : (
                        <>
                            <h2>Rentify:Best place to see property.</h2>
                        </>
                    )}
                </ul>
                </div>
                <div className="nav-icon" onClick={toggleMenu}>
                    <i className={`fas ${isOpen ? 'fa-times' : 'fa-bars'}`}></i>
                </div>     
            </div>
        </nav>
    );
};

export default NavBar;