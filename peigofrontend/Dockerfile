FROM node:18-alpine3.15 AS build
WORKDIR /usr/src/app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build
### STAGE 2: Run ###
FROM nginx:1.17.1-alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /usr/src/app/dist/peigofrontend /usr/share/nginx/html



##FROM nginx:alpine
##COPY dist/peigofrontend/ /usr/share/nginx/html
##COPY nginx.conf /etc/nginx/nginx.conf
