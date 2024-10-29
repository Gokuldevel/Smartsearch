async function search() {
    const query = document.getElementById('searchQuery').value;
    const response = await fetch(`http://localhost:8080/api/search?query=${encodeURIComponent(query)}`);
    const data = await response.json();

    // Clear previous results
    document.querySelector('#youtube-results .result-grid').innerHTML = '';
    document.querySelector('#article-results .result-list').innerHTML = '';
    document.querySelector('#blog-results .result-list').innerHTML = '';
    document.querySelector('#paper-results .result-list').innerHTML = ''; // Clear papers

    // Display YouTube Videos
    data.videos.forEach(video => {
        const card = document.createElement('div');
        card.className = 'video-card';
        card.innerHTML = `
            <iframe src="https://www.youtube.com/embed/${video.id}" 
                    frameborder="0" 
                    allowfullscreen 
                    title="${video.title}"></iframe>
            <h3>${video.title}</h3>
            <a href="https://www.youtube.com/watch?v=${video.id}" target="_blank">Watch Video</a>
        `;
        document.querySelector('#youtube-results .result-grid').appendChild(card);
    });

    // Display Articles
    data.articles.forEach(article => {
        const card = document.createElement('div');
        card.className = 'article-card';
        card.innerHTML = `
            <h3>${article.title}</h3>
            <p>${article.summary}</p>
            <a href="${article.link}" target="_blank">Read More</a>
        `;
        document.querySelector('#article-results .result-list').appendChild(card);
    });

    // Display Blogs
    data.blogs.forEach(blog => {
        const card = document.createElement('div');
        card.className = 'blog-card';
        card.innerHTML = `
            <h3>${blog.title}</h3>
            <p>${blog.summary}</p>
            <a href="${blog.link}" target="_blank">Read More</a>
        `;
        document.querySelector('#blog-results .result-list').appendChild(card);
    });

    data.papers.forEach(paper => {
        const card = document.createElement('div');
        card.className = 'paper-card';
        card.innerHTML = `
            <h3>${paper.title}</h3>
            <p>${paper.summary}</p>
            <a href="${paper.link}" target="_blank">Read More</a>
    `;
        document.querySelector('#paper-results .result-list').appendChild(card);
    });
}
