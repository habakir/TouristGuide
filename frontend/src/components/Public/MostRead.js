import React, { useEffect, useState } from 'react';
import { getMostReadArticles } from '../../api/api';

const MostRead = () => {
    const [articles, setArticles] = useState([]);

    useEffect(() => {
        async function fetchArticles() {
            const data = await getMostReadArticles();
            console.log(data);
            setArticles(data);
        }
        fetchArticles();
    }, []);

    return (
        <div>
            <h1>Most Read</h1>
            <ul>
                {articles.map(article => (
                    <li key={article.id}>
                        <h2>{article.title}</h2>
                        <p>{article.text.substring(0, 100)}...</p>
                        <p>{article.destination ? article.destination.name : 'Unknown Destination'}</p>
                        <p>{article.createdAt}</p>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default MostRead;
