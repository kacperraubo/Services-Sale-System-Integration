from audioop import reverse
import datetime
import json
from os import login_tty
from django.http import HttpResponseRedirect
import requests

from Network.network.models import User


def auth_user(request, services_token):
    # Endpoint to the services API
    if services_token:
        endpoint = f"http://127.0.0.1:8000/api/authorization/{services_token}"
    else:
        return HttpResponseRedirect(reverse('index'))
    
    # Authorization token. Better keep it in environment variables.
    login_token = 'ad478af03101c6011608b4f4521934be508b86a2'
    headers = {
        'Authorization': f"Bearer {login_token}"
    }

    get_response = requests.get(endpoint, headers=headers)
    if get_response.ok:
        # A user email form services API.
        email = json.loads(get_response.content).get('internal_email_address', None)
        midnight = datetime.datetime.combine(datetime.datetime.today() + datetime.timedelta(days=1), datetime.time.min)

        try:
            user = User.objects.get(email=email)
        except User.DoesNotExist:
            user = User.objects.create_user(f"username_{email.split('@')[0]}_{round(datetime.datetime.now().timestamp())}", email, f"{secrets.token_hex(32)}")
            user.save()

        login_tty(request, user)
        response = HttpResponseRedirect(reverse('index'))
        response.set_cookie('services', 'services_cookie', max_age=(midnight - datetime.datetime.now()).seconds)
        return response
    
    return HttpResponseRedirect(reverse('index'))