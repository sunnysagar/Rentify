import { useEffect } from "react";
import { useLocation } from "react-router-dom";

const UsePagePreservation = () => {
    const location = useLocation();
   
    useEffect(() => {
        
        sessionStorage.setItem('currentPath', location.pathname);
        // it will help save current path so upon refresh or reload it will not render the login page despite of authenticated or loggedin
        
    }, [location]);

    return null;
};

export default UsePagePreservation;