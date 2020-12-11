FROM mysql:8
ENV MYSQL_ROOT_PASSWORD 123
ENV MYSQL_DATABASE crudapicheckpointdb
ENV MYSQL_USER user
ENV MYSQL_PASSWORD password
# File from the previous step
COPY setup.sql /docker-entrypoint-initdb.d
EXPOSE 3306
