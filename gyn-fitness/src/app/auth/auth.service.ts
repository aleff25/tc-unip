import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Subject} from 'rxjs/Subject';
import {AuthData} from './auth-data.model';
import {TrainingService} from '../training/training.service';
import {UIService} from '../shared/ui.service';
import {HttpClient} from '@angular/common/http';
import {catchError, map, take} from 'rxjs/operators';
import {of} from 'rxjs/internal/observable/of';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class AuthService {

  baseUrl = 'http://localhost:8080';
  authChange = new Subject<boolean>();
  private isAuthenticated = false;

  constructor(
    private router: Router,
    private http: HttpClient,
    private trainingService: TrainingService,
    private uiService: UIService
  ) {
  }

  registerUser(authData: AuthData) {
    this.uiService.loadingStateChanged.next(true);

    const resourceUrl = `${this.baseUrl}/users`;

    return this.http.post(resourceUrl, authData).pipe(
      take(1),
      map(() => {
        this.uiService.loadingStateChanged.next(false);
        this.authChange.next(true);
      }),
      catchError(err => {
        this.uiService.loadingStateChanged.next(false);
        this.uiService.showSnackbar(err.error.message, null, 3000);
        return of(err);
      })
    );
  }

  initAuthListener() {
    this.authChange.subscribe( user => {
      if (user) {
        this.isAuthenticated = true;
        this.router.navigate(['/training']);
      } else {
        this.trainingService.cancelSubscriptions();
        this.router.navigate(['/login']);
        this.isAuthenticated = false;
      }
    });
  }

  login(authData: AuthData): Observable<any> {
    this.uiService.loadingStateChanged.next(true);

    const resourceUrl = `${this.baseUrl}/users?email=${authData.email}&password=${authData.password}`;

    return this.http.get(resourceUrl).pipe(
      map(() => {
        this.uiService.loadingStateChanged.next(false);
        this.authChange.next(true);
      }),
      catchError(err => {
        this.uiService.loadingStateChanged.next(false);
        this.uiService.showSnackbar(err.error.message, null, 3000);
        return of(err);
      })
    );
  }

  logout() {
    this.authChange.next(false);
  }

  isAuth() {
    return this.isAuthenticated;
  }
}
