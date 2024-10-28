import { computed, Injectable, signal } from "@angular/core";
import { SecurityService } from "../api/security.service";

const LOGINUSER = "com.jdc.balance.user"

@Injectable({providedIn: 'root'})
export class LoginUserState {

  user = signal<LoginUser | undefined>(undefined)
  role = computed(() => this.user()?.role)

  constructor() {
    const storageData = localStorage.getItem(LOGINUSER)

    if(storageData) {
      const loginUser:LoginUser = JSON.parse(storageData)
      this.user.set(loginUser)
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

