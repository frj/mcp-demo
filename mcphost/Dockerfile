FROM golang:1.25
RUN go install github.com/mark3labs/mcphost@latest
RUN adduser mcphost

WORKDIR /home/mcphost
USER mcphost
COPY .mcphost.yml /home/mcphost/
ENTRYPOINT ["/bin/bash"]