import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getArticle, createComment } from '../../api/api';
import '../../styles.css'; // Import the CSS file

const ArticleDetail = () => {
    const { id } = useParams();
    const [article, setArticle] = useState(null);
    const [newComment, setNewComment] = useState({ author: '', text: '' });

    useEffect(() => {
        async function fetchArticle() {
            const data = await getArticle(id);
            setArticle(data);
        }
        fetchArticle();
    }, [id]);

    const handleCommentChange = (e) => {
        const { name, value } = e.target;
        setNewComment({ ...newComment, [name]: value });
    };

    const handleCommentSubmit = async (e) => {
        e.preventDefault();
        console.log('Submitting comment:', newComment); // Debugging info
        try {
            await createComment(id, newComment);
            const data = await getArticle(id);
            setArticle(data);
            setNewComment({ author: '', text: '' });
        } catch (error) {
            console.error('Error creating comment:', error);
        }
    };

    if (!article) {
        return <div className="loading">Loading...</div>;
    }

    return (
        <div className="container">
            <h1>{article.title}</h1>
            <p>{article.text}</p>
            <p>Author: {article.author ? `${article.author.firstName} ${article.author.lastName}` : 'Unknown'}</p>
            <p>Created At: {new Date(article.createdAt).toLocaleString()}</p>
            <p>Activities: {article.activities.map((activity, index) => (
                <span key={index}>
                    <a href={`/activities/${activity}`}>{activity}</a>
                    {index < article.activities.length - 1 && ', '}
                </span>
            ))}</p>
            <h2>Comments</h2>
            <ul>
                {article.comments.map(comment => (
                    <li key={comment.id}>
                        <p>{comment.text}</p>
                        <p>By: {comment.author} on {new Date(comment.createdAt).toLocaleDateString()}</p>
                    </li>
                ))}
            </ul>
            <h2>Add a Comment</h2>
            <form onSubmit={handleCommentSubmit}>
                <input
                    type="text"
                    name="author"
                    value={newComment.author}
                    onChange={handleCommentChange}
                    placeholder="Your name"
                    required
                />
                <textarea
                    name="text"
                    value={newComment.text}
                    onChange={handleCommentChange}
                    placeholder="Your comment"
                    required
                />
                <button type="submit">Submit</button>
            </form>
        </div>
    );
};

export default ArticleDetail;
