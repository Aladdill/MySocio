/**
 * 
 */
package net.mysocio.ui;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mysocio.authentication.UserIdentifier;
import net.mysocio.connection.readers.FacebookSource;
import net.mysocio.connection.readers.ISource;
import net.mysocio.data.IConnectionData;
import net.mysocio.data.SocioUser;
import net.mysocio.data.accounts.Account;
import net.mysocio.data.accounts.FacebookAccount;
import net.mysocio.data.management.ConnectionData;
import net.mysocio.data.management.DataManagerFactory;
import net.mysocio.data.management.IDataManager;
import net.mysocio.data.management.JdoDataManager;
import net.mysocio.data.messages.FacebookMessage;
import net.mysocio.ui.management.CommandExecutionException;
import net.mysocio.ui.managers.basic.DefaultResourcesManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Aladdin
 *
 */
public class LoginHandler extends AbstractHandler {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6174410260385459501L;
	private static final Logger logger = LoggerFactory.getLogger(LoginHandler.class);
	
	/**
	 * @param request
	 * @param responseString
	 * @return
	 * @throws CommandExecutionException
	 */
	protected String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws CommandExecutionException {
		IConnectionData connectionData = new ConnectionData(request);
		String responseString = "";
		String identifierString = connectionData.getRequestParameter("identifier");
		if ("test".equals(identifierString)){
			return handleTestRequest(connectionData);
		}
		if (identifierString != null){
			UserIdentifier identifier = UserIdentifier.valueOf(identifierString);
			if (identifier != null){
				logger.debug("Getting request url  for " + identifier.name());
				responseString = identifier.getAuthManager().getRequestUrl();
				request.getSession().setAttribute("identifier", identifier);
			}else{
				throw new  CommandExecutionException("No login medium was defined");
			}
		}
		String error = connectionData.getRequestParameter("error");
		if (error != null && error.equals("access_denied")){
			throw new CommandExecutionException("Are you sure you want to log in?");
		}
		String code = connectionData.getRequestParameter("code");
		if (code != null){
			UserIdentifier identifier = (UserIdentifier)request.getSession().getAttribute("identifier");
			try {
				Account account = identifier.getAuthManager().login(connectionData);
				SocioUser user = DataManagerFactory.getDataManager().getUser(account, connectionData.getLocale());
				connectionData.setUser(user);
			} catch (Exception e) {
				throw new CommandExecutionException(e);
			}
			request.getSession().removeAttribute("identifier");
			responseString = DefaultResourcesManager.getPage("closingWindow.html");
		}
		return responseString;
	}

	private String handleTestRequest(IConnectionData connectionData) {
		Account account = new FacebookAccount();
		account.setAccountUniqueId("test@test.com");
		account.setUserName("Vasya Pupkin");
		account.setUserpicUrl("images/portrait.jpg");
		JdoDataManager dataManager = (JdoDataManager)DataManagerFactory.getDataManager();
		SocioUser user = dataManager.getUser(account, Locale.ENGLISH);
		FacebookSource fs = new FacebookSource();
		fs.setName("Test Facebook account");
		fs.setUrl("testSourceId");
		fs = (FacebookSource)dataManager.createSource(fs, user);
		user.addSource(fs);
		FacebookSource fs1 = new FacebookSource();
		fs1.setName("Test Facebook account1");
		fs1.setUrl("testSourceId1");
		fs1 = (FacebookSource)dataManager.createSource(fs1, user);
		user.addSource(fs1);
		dataManager.createMessage(createLongTestMessage(fs1));
		dataManager.createMessage(createShortTestMesssage(1, fs1));
		dataManager.createMessage(createShortTestMesssage(2, fs1));
		dataManager.createMessage(createShortTestMesssage(3, fs1));
		dataManager.createMessage(createShortTestMesssage(4, fs1));
		dataManager.createMessage(createShortTestMesssage(5, fs1));
		dataManager.createMessage(createShortTestMesssage(6, fs1));
		dataManager.createMessage(createShortTestMesssage(7, fs1));
		dataManager.createMessage(createShortTestMesssage(8, fs1));
		dataManager.createMessage(createShortTestMesssage(9, fs1));
		dataManager.createMessage(createShortTestMesssage(10, fs1));
		dataManager.updateUnreaddenMessages(user);
		connectionData.setUser(user);
		return "pages/closingWindow.html";
	}

	/**
	 * @return
	 */
	private static FacebookMessage createLongTestMessage(FacebookSource fs1) {
		FacebookMessage message = new FacebookMessage();
		message.setDate(System.currentTimeMillis());
		message.setTitle("Test message Title");
		message.setSourceId(fs1.getId());
		message.setText("Test message text: Человек у Окна - Российская драма со Стояновым в главной роли, неожиданно хороший фильм современного Российского кинематографа." +
				"Das Experiment - Очень сильная драма про эксперимент где разделили две группы на тюремщиков и заключенных. Фильм несколько сгустил краски, для большей зрелищности, но это не мешает. Мне Понравилось." +
				"Fair Game - Дурацкий фильм с хорошими актерами." +
				"Generation П - Лично мне понравилось, но без предварительного прочтения наверное не катит." +
				"Potiche - Неплохая комическая драма с хорошими актерами." +
				"The Next Three days - Фильм держится только на Расселе Кроу, но он сумел таки вытянуть этот моноспектакль." +
				"Biutiful - Дремучая драма с прекрасной игрой актеров, но чернуха к середине начинает зашкаливать на моем чернухометре." +
				"Game of Thrones - Я никогда не любил Мартина ... до этого сериала. После двух или трех игр по книге \"Сага огня и Льда\", после просмотра сериала, я наконец выучил названия домов и понял политическую расстановку. Прекрасная игра актеров, красивые сиськи съемки." +
				"Spartacus: Blood and Send, Spartacus: Gods of the Arena - Здесь красивы и сиськи и письки. Сериал полон откровенных сексуальных сцен и сцен насилия а иногда и сцен сексуального насилия. Кроме этого, актеры играют прекрасно, съемки очень красивые, а бои очень комиксные. \"Кровь и Песок\" фактически первый сезон, но, поскольку у главного актера проблемы со здоровьем, \"Боги Арены\" сняты как приквел. Лично мне, больше понравились \"Боги Арены\" единственное что слегка нервирует флэшбеки из первого сезона, которым, я считаю не место в приквеле, бо они натурально спойлят. В \"Кровь и Песок\" меня слегка утомил героический Спартак и потуги показать моногамное общество в древнем Риме, кроме этих мелочей он тоже заслуживает той высокой оценки, которую ему дали на ИМДБ." +
				"Network (1976) - Старая хорошая драматическая комедия про телевиденье. По наводке boyaguzka" +
				"The Adjustment Bureau - Неплохой фильм с хорошими актерами, слегка вторичен и чем-то Лукьяниновский по идее, но для одноразового просмотра вполне подходит." +
				"El Topo - Очень замудренное авторское кино, досмотрел до конца, но на мой взгляд, после середины смотреть не стоит." +
				"На самом деле я еще слушаю и читаю книжки. По наущению shandi1 недочитал Хаксли \"Контрапункт\", на двадцати процентах я решил, что это слишком нудно. Прослушал Кинга \"Стрелок\" смешанные чувства, видимо из-за того что книга была переработана, очень неровный стиль. Во второй книге \"Извлечение Троих\" у него почему-то не стреляют мокрые патроны, наверное у Стрелка револьверы с дульным зарядом. размышляю продолжать ли. Прэттчет как всегда отжигает. Дослушал всю серию Ехо, Фрай жестоко обошлась с читателями в последней книги, подозреваю это месть за фэновый спам \"Тетенка, напишите еще книжечку\". Сейчас читаю \"Обжеора хохотун\", на очереди Олди \"Алюмен\" \"Urbis et Orbis\"." +
				"Ну и на последок, понравившаяся мне суфийская притча и соответсвующего аудиосборника." +
				"Однажды один дервиш пришёл в деревню. Жители сказали ему, что их раджа тоже дервиш. Приезжий дервиш попросил встречи с ним, так как удивился, как раджа, богатейший человек может быть дервишем. Раджа принял его. По определённым знакам они узнали друг в друге дервишей и стали обращаться друг к другу как брат с братом." +
				"— Брат, я не понимаю, — сказал дервиш, — у тебя огромный дворец, прекрасный гарем, богатство… Как при всём этом ты являешься дервишем? Вот у меня ничего нет, я хожу по миру и учу людей." +
				"— Ой, как хорошо. Какая у тебя хорошая жизнь, я тоже так хочу, — сказал раджа." +
				"— Тогда пошли со мной, — ответил дервиш." +
				"— Пошли, — сказал раджа, встал и направился к воротам." +
				"— Эй, постой, — воскликнул дервиш, — у меня в твоём дворце остались чаша и посох. Я должен их взять.");
		message.setLink("http://aladdill.livejournal.com/207656.html");
		return message;
	}

	/**
	 * @param i 
	 * @return
	 */
	private static FacebookMessage createShortTestMesssage(int i, ISource source) {
		FacebookMessage message = new FacebookMessage();
		message.setLink(source.getUrl() + "Test message Title"  + i);
		message.setDate(System.currentTimeMillis());
		message.setTitle("Test message Title" + i);
		message.setSourceId(source.getId());
		message.setText("Test message text: Человек у Окна - Российская драма со Стояновым в главной роли, неожиданно хороший фильм современного Российского кинематографа." +
				"Das Experiment - Очень сильная драма про эксперимент где разделили две группы на тюремщиков и заключенных. Фильм несколько сгустил краски, для большей зрелищности, но это не мешает. Мне Понравилось." +
				"Fair Game - Дурацкий фильм с хорошими актерами.");
		return message;
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}
}
