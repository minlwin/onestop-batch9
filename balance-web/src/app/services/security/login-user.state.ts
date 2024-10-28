import { computed, Injectable, signal } from "@angular/core";
import { SecurityService } from "../api/security.service";

const LOGINUSER = "com.jdc.balance.user"

@Injectable({providedIn: 'root'})
export class LoginUserState {

  user = signal<LoginUser | undefined>(undefined)
  role = computed(() => this.user()?.role)

  constructor(security:SecurityService) {
    const storageData = localStorage.getItem(LOGINUSER)

    if(storageData) {
      const loginUser:LoginUser = JSON.parse(storageData)

      security.validate({type: 'Refresh', token: loginUser.refreshToken}).subscribe(result => {
        if(result.valid) {
          security.refresh({token: loginUser.refreshToken}).subscribe(refreshed => {
            this.setUser(refreshed)
          })
        }
      })
    }
  }

  setUser(user:LoginUser) {
    this.user.set(user)
    localStorage.setItem(LOGINUSER, JSON.stringify(user))
  }

  signOut() {
    this.user.set(undefined)
    localStorage.removeItem(LOGINUSER)
  }
}

export interface LoginUser {
  loginId:string,
  name:string,
  role:'Admin' | 'Member',
  accessToken:string,
  refreshToken:string
}

