import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getDestinations } from '../../api/api';

const AllDestinations = () => {
    const [destinations, setDestinations] = useState([]);

    useEffect(() => {
        async function fetchDestinations() {
            const data = await getDestinations();
            setDestinations(data);
        }
        fetchDestinations();
    }, []);

    return (
        <div>
            <h1>All Destinations</h1>
            <ul>
                {destinations.map(destination => (
                    <li key={destination.id}>
                        <Link to={`/destination/${destination.id}`}>{destination.name}</Link>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default AllDestinations;
