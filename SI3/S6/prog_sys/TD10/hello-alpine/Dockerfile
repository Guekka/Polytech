# alpine
FROM alpine:3.17.3

# install libc6-compat
RUN apk add --no-cache libc6-compat

COPY ./hello.exe /hello.exe

CMD ["/hello.exe"]
