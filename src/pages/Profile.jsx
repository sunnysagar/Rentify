import React, { useEffect, useState} from "react";
import axios from "axios";


const Profile = () =>{

    const [userInfo, setUserInfo] = useState(null);


    const fetchUserInfo = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await axios.get('http://localhost:8010/rentify/users/me', {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            console.log('User Info:', response.data);
            setUserInfo(response.data);
        } catch (error) {
            console.error('Error fetching user info', error);
            if (error.response) {
                console.error('Response data:', error.response.data);
                console.error('Response status:', error.response.status);
                console.error('Response headers:', error.response.headers);
            } else if (error.request) {
                console.error('Request data:', error.request);
            } else {
                console.error('Error message:', error.message);
            }
        }
    };

    // useEffect(() => {
    //     const fecthUserInfo = async () => {
    //         try{
    //             const token = localStorage.getItem('token');
    //             const response = await axios.get("http://localhost:8010/rentify/users/me", {
    //                 headers: {
    //                     Authorization: `Bearer ${token}`
    //                 }
    //             });

    //             setUserInfo(response.data);

    //         }catch(error){
    //             console.error('Failed to fetch user info', error);

    //         }
    //     };

    //     fecthUserInfo();
    // }, []);

    useEffect(() => {
        fetchUserInfo();
    }, []);


    return (
        <div>
            {userInfo ? (
                <div>
                    <h1>Welcome, {userInfo.firstName} {userInfo.lastName}</h1>
                    <p>Email: {userInfo.email}</p>
                    <p><strong>Mobile:</strong>{userInfo.phoneNumber}</p>
                    {/* Render other user info as needed */}
                </div>
            ) : (
                <p>Loading...</p>
            )}
        </div>
    );

    // return(
    //     <div>
    //         <h1>User Profile</h1>
    //         <p><strong>FirstName:</strong>{userInfo.firstName}</p>
    //         <p><strong>LastName:</strong>{userInfo.lastName}</p>
    //         <p><strong>Email:</strong>{userInfo.email}</p>
    //         <p><strong>Mobile:</strong>{userInfo.phoneNumber}</p>
    //     </div>
    // );
};

export default Profile;