import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Navbar = () => {
    const navigate = useNavigate();
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        // Set initial login state based on localStorage
        setIsLoggedIn(!!localStorage.getItem('token'));

        // Update the state when localStorage changes
        const handleStorageChange = () => {
            setIsLoggedIn(!!localStorage.getItem('token'));
        };

        window.addEventListener('storage', handleStorageChange);
        return () => {
            window.removeEventListener('storage', handleStorageChange);
        };
    }, []);

    const handleLogout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('role');
        localStorage.removeItem('user');
        setIsLoggedIn(false);
        navigate('/login');
    };

    const role = localStorage.getItem('role');

    return (
        <nav>
            <ul>
                <li><Link to="/">Home</Link></li>
                <li><Link to="/most-read">Most Read</Link></li>
                <li><Link to="/destinations">Destinations</Link></li>
                {isLoggedIn && (
                    <>
                        <li><Link to="/destinations/manage">Manage Destinations</Link></li>
                        <li><Link to="/articles">Articles</Link></li>
                        {role === 'admin' && <li><Link to="/users">Users</Link></li>}
                    </>
                )}
                {isLoggedIn ? (
                    <li><button onClick={handleLogout}>Logout</button></li>
                ) : (
                    <li><Link to="/login">Login</Link></li>
                )}
            </ul>
        </nav>
    );
};

export default Navbar;
