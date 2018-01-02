# Frontend-boilerplate-ng1




# Using Docker & Nginx to proxy dev, eg:

```
docker run --name dm-nginx -p 8888:8888 -v ~/Documents/remoteWorkspace/git/frontend-boilerplate-ng1/nginx.conf:/etc/nginx/nginx.conf:ro -d nginx
```