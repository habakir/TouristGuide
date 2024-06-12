import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getArticlesByDestination } from '../../api/api';

const DestinationArticles = () => {
    const { id } = useParams();
    const [articles, setArticles] = useState([]);

    useEffect(() => {
        async function fetchArticles() {
            const data = await getArticlesByDestination(id);
            setArticles(data);
        }
        fetchArticles();
    }, [id]);

    return (
        <div>
            <h1>Articles for Destination {id}</h1>
            <ul>
                {articles.map(article => (
                    <li key={article.id}>
                        <h2>{article.title}</h2>
                        <p>{article.text.substring(0, 100)}...</p>
                        <p>{article.createdAt}</p>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default DestinationArticles;
