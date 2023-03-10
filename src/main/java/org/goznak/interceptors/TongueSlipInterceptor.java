package org.goznak.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class TongueSlipInterceptor implements HandlerInterceptor {
    private static final String[] tongueSlips = {
                "Жизнь отдавай, а тайны не выдавай",
                "Кто открывает тайну, тот нарушает верность",
                "Тайное слово в своих устах держи",
                "Не давай волю языку на пиру, во хмелю, в беседе и гневе",
                "Кто сдержит свой язык, тот сохранит голову",
                "О чем не спрашивают, о том не говори",
                "За собою слово не удержал — за людьми не удерживай",
                "Чужой тайны не разглашай, а свою блюди",
                "Хорошее смолчится, а худо огласится",
                "Не звони в большой колокол — не разглашай тайны",
                "Голову отдай, а тайну никогда",
                "Кто открывает тайну, тот нарушает верность",
                "Зря не болтай, гляди в оба, военную тайну храни до гроба",
                "Голову сложу, а тайны не скажу",
                "Храни тайну не только замком, но и языком",
                "О многом и мать не должна знать",
                "Где двое тайно говорят, тут третий говорун не брат",
                "Один — тайна, два — полтайны, три — нет тайны",
                "Береги военную тайну как зеницу ока",
                "Кто разглашает тайну, тот изменяет Родине",
                "Чужой тайны не открывай и свою береги",
                "Добрый молодец никогда не выдает тайны",
                "Среди людей самый слабый тот, который не умеет сохранить свою тайну",
                "Он охотнее отдаст голову, чем свою тайну",
                "Кто рассказывает тайну, тот теряет всякое доверие",
                "Тайна — та же сеть: ниточка порвется — вся расползется",
                "Знает кум, да кума, да людей полсела",
                "Узнал сосед — узнает весь свет",
                "Голову сложу, а тайну не скажу",
                "Не выдавай тайны другу, у него тоже найдутся друзья",
                "Нет той тайны, чтобы не была явна",
                "Вино входит — тайна уходит",
                "Знает один — тайна, знают двое — не тайна",
                "Даже умирая, не открывай врагу своей тайны (кирг)",
                "Бойся обидеть друга и выдать тайну врагу (башк)",
                "В каждой голове есть своя тайна (татск)",
                "Когда вино входит — тайна выходит (арм)",
                "Если хочешь скрыть тайну от врага, не говори ее даже другу (татар)",
                "Тайна, известная троим, уже не тайна (адыг)",
                "Другу сказал слово — и враг узнал твою тайну (узб)",
                "Не всегда доверяй свою тайну другу, ибо у твоего друга есть тоже друг (азерб, кирг, узб, каракалпакская, татск, туркм, тадж)",
                "Пусть голова пропадает, но тайна сохранится (тадж)",
                "Врагу душу отдавай, тайну не выдавай (узб)",
                "Кто не может держать в тайне сказанное ему — полумужчина (адыг)",
                "Лесу и темной ночи не доверяй своей тайны (адыг)",
                "Друзей, с которыми можно делиться тайной, — много, но сохранить ее может только один (казах)",
                "Слово «не знаю» дороже тысячи рублей (чеч)",
                "Враг смеется — твою тайну выведал (узб)",
                "Тайное слово не доверяй даже своей подушке (удм)",
                "Что прошло через одни уста, то идет через сотню (кабард)",
                "Тайное слово и жене не говори (калм)",
                "Тайного слова не выпускай из уст (груз)",
                "Не держи у себя врага, чтобы не узнал твои тайны (туркм)",
                "Свою тайну сохраняй, чужую не разглашай (груз)",
                "Не трудно победить врага, если знаешь его тайны (груз)",
                "Я сказал слово другу — и мою тайну узнал враг (узб)",
                "Незнающему не говори, не умеющему хранить тайну не доверяй (башк)",
                "Тайна — твой пленник (узб)",
                "Если в сердце своем тайна не удержится, то и в сердце чужом она не удержится (узб)",
                "Своим не лги, чужим не открывай своей тайны (казах)",
                "Если на своем языке не удержишь, то на чужом не утаишь (укр)",
                "Не каждое ухо может тайну слушать (груз)",
                "Кто в открытые ворота не войдет? (татар)"};
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if(modelAndView != null) {
            modelAndView.getModel().put("tongueSlip", getTongueSlip());
        }
    }
    private String getTongueSlip(){
        return tongueSlips[(int)(Math.random() * (tongueSlips.length))];
    }
}