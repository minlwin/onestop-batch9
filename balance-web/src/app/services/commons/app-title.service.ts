import { Injectable } from "@angular/core";
import { Title } from "@angular/platform-browser";
import { DefaultTitleStrategy, RouterStateSnapshot } from "@angular/router";

@Injectable({providedIn: 'root'})
export class AppTitleService extends DefaultTitleStrategy {

  constructor(title:Title) {
    super(title);
  }

  override updateTitle(snapshot: RouterStateSnapshot): void {
    const titleValue = this.buildTitle(snapshot)
    this.title.setTitle(`Balance | ${titleValue}`)
  }
}
