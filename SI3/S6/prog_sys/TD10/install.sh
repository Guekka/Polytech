#!/bin/sh

# Cleanup oldies if any exists
sudo apt-get purge -y docker docker-engine docker.io containerd runc 2> /dev/null
sudo apt-get purge docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin docker-ce-rootless-extras 2>/dev/null

# Install docker
sudo apt-get install -y curl
curl -fsSL https://get.docker.com | sudo sh

# Add current user to docker group
sudo usermod -aG docker $USER

# Active les changement des groupes 
newgrp docker

# Activate docker service to start automatically
sudo systemctl enable docker.service
sudo systemctl enable containerd.service