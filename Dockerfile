FROM postgres
ENV POSTGRES_USER devapps
ENV POSTGRES_PASSWORD pass
ENV POSTGRES_DB devapps
COPY init.sql /docker-entrypoint-initdb.d/
