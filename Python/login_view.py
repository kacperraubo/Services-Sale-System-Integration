from audioop import reverse
from django.contrib.auth import authenticate, login
from django.http import HttpResponseRedirect
from django.shortcuts import render

from Network.network.views import auth_user

def login_view(request):
    if request.method == "POST":
        # Attempt to sign user in
        username = request.POST["username"]
        password = request.POST["password"]
        user = authenticate(request, username=username, password=password)

        # Check if authentication successful
        if user is not None:
            login(request, user)
            return HttpResponseRedirect(reverse("index"))
        else:
            return render(request, "app/login.html", {
                "message": "Invalid username and/or password."
            })
    # Check if request was sent from Services
    if request.GET.get('services'):
        return auth_user(request, request.GET['services'])
    
    return render(request, "network/login.html")